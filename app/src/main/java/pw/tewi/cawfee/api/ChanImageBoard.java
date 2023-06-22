package pw.tewi.cawfee.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import pw.tewi.cawfee.models.BoardList;
import pw.tewi.cawfee.models.Catalog;
import pw.tewi.cawfee.models.Post;
import pw.tewi.cawfee.models.Thread;
import pw.tewi.cawfee.models.ThreadList;

/**
 * A class for imageboards with API's similar to 4chan's
 */

@Getter
@AllArgsConstructor
@Accessors(fluent=true)
public abstract class ChanImageBoard implements ImageBoard {
    @NonNull private final String apiEndpoint;
    @NonNull private final String imageEndpoint;
    @NonNull private final String staticEndpoint;

    @NonNull @Getter(onMethod=@__(@Override)) private final String name;
    @NonNull @Getter(AccessLevel.NONE) private final Gson gson;

    private static Optional<String> get(@NonNull final String uri) {
        try {
            var connection = (HttpURLConnection) new URL(uri).openConnection();
            connection.setRequestMethod("GET");

            var response = new BufferedReader(new InputStreamReader(connection.getInputStream()))
                          .lines()
                          .collect(Collectors.joining());

            connection.disconnect();
            Log.d("ImageBoard::get", "Recieved response " + response);

            return Optional.of(response);
        } catch (IOException e) {
            Log.e("ImageBoard::get",
                  "Get request to `" + uri + "`\nfailed with: " + e.getMessage());

            return Optional.empty();
        }
    }


    @NonNull private String createUrl(@NonNull final String... path) {
        return String.join("/", path);
    }

    @NonNull private String createApiUrl(@NonNull final String... path) {
        return apiEndpoint + "/" + createUrl(path) + ".json";
    }

    private void setPostUrls(@NonNull Post post, final String boardName) {
        var ext = post.getExt();

        if (ext == null) { return; }

        var partialImageUrl = createUrl(imageEndpoint, boardName, Long.toString(post.getTim()));
        post.setImageUrl(partialImageUrl + ext);
        post.setThumbnailUrl(partialImageUrl + "s.jpg");
    }

    private <T> Optional<T> fetchClass(
        @NonNull final Class<T> classType,
        @NonNull final String url) {

        try {
            return get(url).map(response -> gson.fromJson(response, classType));

        } catch (JsonSyntaxException jse) {
            Log.e("ImageBoard::fetchClass", "Failed to parse " + classType, jse);

            return Optional.empty();
        }
    }

    @Override
    public Optional<BoardList> boards() {
        return fetchClass(BoardList.class, createApiUrl("boards"));
    }

    @Override
    public Optional<Catalog> catalog(@NonNull final String boardName) {
        var maybeCatalog = fetchClass(Catalog.class, createApiUrl(boardName, "catalog"));

        maybeCatalog.ifPresent(catalog -> {
            catalog.stream()
                   .flatMap(page -> page.getThreads().stream())
                   .forEach(post -> setPostUrls(post, boardName));
        });

        return maybeCatalog;
    }

    @Override
    public Optional<ThreadList> threads(@NonNull final String boardName) {
        return fetchClass(ThreadList.class, createApiUrl(boardName, "threads"));
    }

    @Override
    public Optional<Thread> thread(
        @NonNull final String boardName,
        @NonNull final String threadNo) {

        var maybeThread = fetchClass(Thread.class, createUrl(apiEndpoint, boardName, "thread", threadNo + ".json"));

        maybeThread.ifPresent(thread -> {
            thread.getPosts()
                  .forEach(post -> setPostUrls(post, boardName));
        });

        return maybeThread;
    }
}
