package pw.tewi.cawfee.ui.catalog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.NonNull;
import pw.tewi.cawfee.databinding.FragmentCatalogBinding;


/**
 * A fragment used to display the catalog and control its behavior
 */
@AndroidEntryPoint
public final class CatalogFragment extends Fragment {
    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject CatalogAdapter adapter;
    private GridView gridView;
    private CatalogViewModel viewModel;

    private FragmentCatalogBinding binding;
    private SwipeRefreshLayout layout;

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {

        binding = FragmentCatalogBinding.inflate(inflater, container, false);
        gridView = binding.catalogGridView;
        layout = binding.catalogLayout;

        layout.setOnChildScrollUpCallback((__, ___) -> detectRefresh());
        layout.setOnRefreshListener(this::refreshCatalog);

        // track changes in the view's size
        gridView.getViewTreeObserver()
                .addOnGlobalLayoutListener(() -> {
                    adapter.gridWidth(gridView.getWidth())
                           .gridHeight(gridView.getHeight());
                });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CatalogViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.dispose();
        binding = null;
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
     * Fires on a screen pull-down
     */
    public void refreshCatalog() {
        layout.setRefreshing(true);

        disposable.add(
            Observable
                .defer(() -> Observable.just(viewModel.fetCatalogData()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(liveData -> {
                    adapter.clear();
                    adapter.addAll(liveData.getValue()
                                           .stream()
                                           .flatMap(p -> p.getThreads().stream())
                                           .collect(Collectors.toList()));

                    layout.setRefreshing(false);
                }));
    }
}
