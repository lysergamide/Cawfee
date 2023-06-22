package pw.tewi.cawfee.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * a comprehensive list of all threads+attributes on a board
 * <br>
 * Every thread is grouped by page
 * <br>
 * <a href="https://github.com/4chan/4chan-API/blob/master/pages/Catalog.md">
 * GET {board}/catalog.json
 * </a>
 */
public final class Catalog extends ArrayList<Catalog.CatalogPage> {

    @Data
    public static final class CatalogPage {
        private int page;
        private List<Post> threads;
    }
}
