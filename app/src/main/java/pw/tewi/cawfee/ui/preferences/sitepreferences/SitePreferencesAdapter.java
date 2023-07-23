package pw.tewi.cawfee.ui.preferences.sitepreferences;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import pw.tewi.cawfee.R;
import pw.tewi.cawfee.api.ImageBoard;
import pw.tewi.cawfee.db.BoardDao;
import pw.tewi.cawfee.models.Board;
import pw.tewi.cawfee.ui.preferences.CawfeePreferenceManager;

public final class SitePreferencesAdapter extends ArrayAdapter<ImageBoard> {
    private final CompositeDisposable     disposable = new CompositeDisposable();
    private final CawfeePreferenceManager preferenceManager;
    private final BoardDao                boardDao;

    @Inject
    public SitePreferencesAdapter(
        @ActivityContext Context context,
        CawfeePreferenceManager cawfeePreferenceManager,
        BoardDao boardDao) {

        super(context, 0, new ArrayList<>());
        this.preferenceManager = cawfeePreferenceManager;
        this.boardDao          = boardDao;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View prefView, @NonNull ViewGroup parent) {
        if (prefView == null) {
            prefView = LayoutInflater.from(getContext())
                                     .inflate(R.layout.fragment_site_preferences_item, parent, false);

            prefView.setTag(ViewHolder.builder()
                                      .itemToggle(prefView.findViewById(R.id.site_pref_item_toggle))
                                      .settingsButton(prefView.findViewById(R.id.site_pref_item_settings))
                                      .dragHandle(prefView.findViewById(R.id.site_pref_item_drag_handle))
                                      .build());
        }

        final var imageBoard = getItem(position);

        var holder = (ViewHolder) prefView.getTag();

        holder.settingsButton.setText(imageBoard.name());
        holder.settingsButton.setOnClickListener((__) -> {
            var dialog = new AlertDialog.Builder(getContext()).create();
            var listView = new ListView(getContext());

            var adapter = new ArrayAdapter<Board>(getContext(), 0, new ArrayList<>()) {

                @NonNull @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    final var checkbox = new CheckBox(getContext());
                    final var board = getItem(position);
                    final var str = board.getBoard() + " - " + board.getMeta_description(); // linter complains

                    checkbox.setText(str);
                    return checkbox;
                }
            };

            if (boardDao.rowCount().blockingGet() == 0) {
                disposable.add(
                    Observable.defer(() -> Observable.just(imageBoard.boards()))
                              .subscribeOn(Schedulers.io())
                              .observeOn(Schedulers.io())
                              .subscribe(optionalBoardList ->
                                             optionalBoardList.ifPresent(boardList -> {
                                                 var boards = boardList.getBoards();
                                                 boardDao.insertAll(boards);
                                                 adapter.addAll(boards);
                                             })));

            } else {
                disposable.add(boardDao.getAllBoards()
                                       .subscribeOn(Schedulers.io())
                                       .observeOn(Schedulers.io())
                                       .subscribe(adapter::addAll));
            }

            var layout = new FrameLayout(getContext());
            listView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(listView);
            listView.setAdapter(adapter);

            dialog.setView(layout);
            dialog.show();
        });

        holder.itemToggle.setChecked(preferenceManager.isSiteEnabled(imageBoard.name()));
        holder.itemToggle.setOnCheckedChangeListener((__, checked) -> {
            if (checked) {
                preferenceManager.enableSite(imageBoard.name());
            } else {
                preferenceManager.disableSite(imageBoard.name());
            }
        });

        holder.dragHandle.setClickable(true);

        return prefView;
    }

    @Builder
    @RequiredArgsConstructor
    private static class ViewHolder {
        final SwitchMaterial itemToggle;
        final MaterialButton settingsButton;
        final MaterialButton dragHandle;
    }
}
