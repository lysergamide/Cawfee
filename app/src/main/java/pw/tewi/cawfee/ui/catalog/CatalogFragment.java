package pw.tewi.cawfee.ui.catalog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Optional;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.NonNull;
import pw.tewi.cawfee.databinding.FragmentCatalogBinding;
import pw.tewi.cawfee.models.Catalog;


/**
 * A fragment used to display the catalog and control its behavior
 */
@AndroidEntryPoint
public final class CatalogFragment extends Fragment {
    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject CatalogAdapter   adapter;
    private CatalogViewModel viewModel;

    private GridView               gridView;
    private SwipeRefreshLayout     layout;

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {

        var binding  = FragmentCatalogBinding.inflate(inflater, container, false);
        gridView = binding.gridView;
        layout   = binding.catalogLayout;

        layout.setOnChildScrollUpCallback((__, ___) -> detectRefresh());
        layout.setOnRefreshListener(this::refreshCatalog);

        // Update the adapter's dimensions when the screen changes size
        gridView.getViewTreeObserver()
                .addOnGlobalLayoutListener(
                    () -> adapter.gridWidth(gridView.getWidth())
                                 .gridHeight(gridView.getHeight()));

        gridView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CatalogViewModel.class).activeBoard("g");
        refreshCatalog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.dispose();
    }

    /**
     * Check if the catalog can be pulled down <br>
     * (i.e. it is not scrolled to the beginning) <br>
     *
     * @return True if the catalog can scroll up, false if it's scrolled all the way up
     */
    private boolean detectRefresh() {
        if (gridView.getFirstVisiblePosition() == 0) {
            return Optional.ofNullable(gridView.getChildAt(0))
                           .map(firstChild -> firstChild.getTop() < gridView.getPaddingTop())
                           .orElse(false);
        }

        return true;
    }

    /**
     * Update the catalog if it has changed
     */
    public void refreshCatalog() {
        layout.setRefreshing(true);

        disposable.add(
            Observable.defer(() -> Observable.just(viewModel.fetCatalogData()))
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(this::updateCatalog));
    }

    /**
     * Replace posts in catalog with new data
     *
     * @param liveData LiveData containing the new catalog state
     */
    private void updateCatalog(LiveData<Catalog> liveData) {
        Optional.ofNullable(liveData.getValue())
                .ifPresent(catalog -> {
                    adapter.clear();
                    adapter.addAll(catalog.flattenedPosts());
                });

        layout.setRefreshing(false);
    }
}
