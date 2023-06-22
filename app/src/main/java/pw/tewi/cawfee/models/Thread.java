package pw.tewi.cawfee.models;

import java.util.List;

import lombok.Data;

/**
 * a representation of a single OP and all the replies, which form a thread
 * <br>
 * <a href="https://github.com/4chan/4chan-API/blob/master/pages/Threads.md">
 * GET {board}/thread/{op ID}.json
 * </a>
 */
@Data
public final class Thread {
    private List<Post> posts;
}
