package pw.tewi.cawfee.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * <a href="https://github.com/4chan/4chan-API/blob/master/pages/Threadlist.md"/>
 * GET {board}/threads.json
 * </a>
 * <p>
 * comprehensive list of all threads that contains:
 * <p>
 * <ul>
 *  <li> The thread OP number </li>
 *  <li> he index page the thread is currently on </li>
 *  <li> UNIX timestamp marking the last time the thread was modified </li>
 *  <li> he number of replies a thread has </li>
 * </ul>
 */
public final class ThreadList extends ArrayList<ThreadList.Page> {

    @Data
    public static final class Page {
        private int page;
        private List<ThreadInfo> threads;

        @Data
        public static final class ThreadInfo {
            private int no;
            private long last_modified;
            private int replies;
        }
    }
}

