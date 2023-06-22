package pw.tewi.cawfee.api;

import java.util.Optional;

import lombok.NonNull;
import pw.tewi.cawfee.models.BoardList;
import pw.tewi.cawfee.models.Catalog;
import pw.tewi.cawfee.models.Thread;
import pw.tewi.cawfee.models.ThreadList;

/**
 * Interface representing an image board.
 * Provides methods to retrieve information about boards, catalogs, threads, and posts.
 */
public interface ImageBoard {
    /**
     * A list of all boards and their attributes.
     *
     * @return optional containing a BoardList on a successful request or an empty optional on failure
     */
    Optional<BoardList> boards();

    /**
     * Get a boards catalog. Includes all OPs and their preview replies.
     *
     * @param boardName the name of the board (e.g. 'g')
     * @return optional containing a Catalog on a successful request or an empty optional on failure
     */
    Optional<Catalog> catalog(@NonNull final String boardName);

    /**
     * A summarized list of all threads on a board including thread numbers,
     * their modification time and reply count.
     *
     * @param boardName the name of the board (e.g. 'g')
     * @return optional containing a ThreadList on a successful request or an empty optional on failure
     */
    Optional<ThreadList> threads(@NonNull final String boardName);

    /**
     * A full list of posts in a single thread.
     *
     * @param boardName the board to fetch the thread from (e.g. 'g')
     * @param threadNo  the OP's number (e.g. '76759434')
     * @return optional containing a thread on a successful request or an empty optional on failure
     */
    Optional<Thread> thread(@NonNull final String boardName, @NonNull final String threadNo);

    String name();
}
