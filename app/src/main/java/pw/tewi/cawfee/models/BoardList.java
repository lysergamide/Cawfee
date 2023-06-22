package pw.tewi.cawfee.models;


import java.util.List;

import lombok.Data;

/**
 * a comprehensive list of all boards and their major settings
 * <br>
 * <a href="https://github.com/4chan/4chan-API/blob/master/pages/Boards.md">
 * GET /boards.json
 * </a>
 */
@Data
public final class BoardList {
    private List<Board> boards;
}
