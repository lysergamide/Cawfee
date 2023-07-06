package pw.tewi.cawfee.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * @return All of the posts in the catalog as a simple list
     */
    public List<Post> flattenedPosts() {
        return stream().flatMap(post -> post.getThreads().stream()).collect(Collectors.toList());
    }

    @Data
    public static final class CatalogPage {
        private int page;
        private List<Post> threads;
    }
}
