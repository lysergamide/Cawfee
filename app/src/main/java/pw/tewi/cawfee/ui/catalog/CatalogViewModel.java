package pw.tewi.cawfee.ui.catalog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.NonNull;
import pw.tewi.cawfee.api.ImageBoard;
import pw.tewi.cawfee.models.Catalog;
import pw.tewi.cawfee.preferences.PreferenceManager;


/**
 * A Viewmodel responsible for managing catalog data
 */
@HiltViewModel
public final class CatalogViewModel extends ViewModel {
    @NonNull private final MutableLiveData<Catalog> cachedCatalog = new MutableLiveData<>(new Catalog());
    @NonNull private final ImageBoard imageBoard;
    @NonNull private final PreferenceManager preferenceManager;

    @NonNull private String activeBoard;

    @Inject
    public CatalogViewModel(@NonNull ImageBoard imageBoard, @NonNull PreferenceManager preferenceManager) {
        this.imageBoard = imageBoard;
        this.preferenceManager = preferenceManager;
        activeBoard = preferenceManager.lastBoard();
    }

    /**
     * Fetches the catalog data for the active board.
     *
     * @return LiveData object representing the catalog data
     * <br>
     * If the active board is empty, the cached catalog data is returned.
     */
    public LiveData<Catalog> fetCatalogData() {
        if (!activeBoard.isEmpty()) {
            imageBoard.catalog(activeBoard)
                      .ifPresent(cachedCatalog::postValue);
        }

        return cachedCatalog;
    }

    /**
     * Sets the active board and updates the corresponding preference
     *
     * @param newBoard The new active board.
     * @return The updated CatalogViewModel instance.
     */
    public CatalogViewModel activeBoard(String newBoard) {
        activeBoard = newBoard;
        preferenceManager.lastBoard(activeBoard);

        return this;
    }
}
