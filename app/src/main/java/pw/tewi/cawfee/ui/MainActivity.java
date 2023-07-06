package pw.tewi.cawfee.ui;

import static androidx.appcompat.R.layout.support_simple_spinner_dropdown_item;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import pw.tewi.cawfee.R;
import pw.tewi.cawfee.databinding.ActivityMainBinding;
import pw.tewi.cawfee.db.CawfeeDatabase;
import pw.tewi.cawfee.models.Board;
import pw.tewi.cawfee.ui.preferences.ChanPreferenceManager;
import pw.tewi.cawfee.ui.preferences.sitepreferences.SitePreferencesActivity;

/**
 * Entry point for Cawfee
 */

@AndroidEntryPoint
public final class MainActivity extends AppCompatActivity {
    private final CompositeDisposable disposable = new CompositeDisposable();

    ActivityMainBinding binding;
    Toolbar             toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        toolbar = binding.activityMainToolbar;

        setSupportActionBar(toolbar);
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu __) {
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle(new ChanPreferenceManager(this).lastBoard());

        final var boardDao = Room.databaseBuilder(this, CawfeeDatabase.class, "boards").build().boardDao();
        final var spinner = (Spinner) binding.activityMainToolbar.findViewById(R.id.activity_main_toolbar_spinner);
        final var adapter = new ArrayAdapter<String>(this, support_simple_spinner_dropdown_item);

        // Fetch the board list from the db, needs to be on a separate thread
        disposable.add(
            boardDao.getAllBoards()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        boards -> {
                            adapter.addAll(
                                boards.stream()
                                      .map(Board::getBoard)
                                      .collect(Collectors.toList()));
                        }
                    ));
        //        disposable.add(
        //            Observable.defer(() -> Observable.just(boardDao.getAllBoards()))
        //                      .subscribeOn(Schedulers.io())
        //                      .observeOn(AndroidSchedulers.mainThread())
        //                      .subscribe(boards -> {
        //                          adapter.addAll(
        //                              boards.stream()
        //                                    .map(Board::getBoard)
        //                                    .collect(Collectors.toList()));
        //                      }));

        spinner.setAdapter(adapter);
        spinner.setPrompt("boards");

        return true;
    }

    @Override public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final var id = item.getItemId();

        // return true if we navigate somewhere
        if (id == R.id.menu_main_site_settings) {
            startActivity(new Intent(this, SitePreferencesActivity.class));
        }
        else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        binding = null;
    }
}
