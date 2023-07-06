package pw.tewi.cawfee.models;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pw.tewi.cawfee.hilt.components.DaggerGsonComponent;

/**
 * Represents an individual board
 * <br>
 * Also defines a board entity for the DB
 * <br>
 * <a href="https://github.com/4chan/4chan-API/blob/master/pages/Boards.md">
 * GET /boards.json
 * </a>
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public final class Board {
    @Ignore private static final Gson gson = DaggerGsonComponent.create().gson();

    @PrimaryKey @NonNull private String board;

    private String site;
    private String meta_description;
    private String title;

    private int ws_board;
    private int per_page;
    private int pages;
    private int max_filesize;
    private int max_webm_filesize;
    private int max_comment_chars;
    private int max_webm_duration;
    private int bump_limit;
    private int image_limit;

    @Embedded(prefix="cooldown_") private Cooldowns cooldowns;

    private int spoilers;
    private int custom_spoilers;
    private int is_archived;

    @TypeConverters(BoardFlagsConverter.class) private Map<String, String> board_flags;

    private int country_flags;
    private int user_ids;
    private int oekaki;
    private int sjis_tags;
    private int code_tags;
    private int math_tags;
    private int text_only;
    private int forced_anon;
    private int webm_audio;
    private int require_subject;
    private int min_image_width;
    private int min_image_height;

    @NonNull @Ignore @Override public String toString() { return board; }

    @Getter
    @Setter
    @NoArgsConstructor
    public static final class Cooldowns {
        int threads;
        int replies;
        int images;
    }

    public static class BoardFlagsConverter {
        @TypeConverter
        public static Map<String, String> fromString(String value) {
            return gson.fromJson(value, new TypeToken<Map<String, String>>() { }.getType());
        }

        @TypeConverter
        public static String toString(Map<String, String> map) {
            return gson.toJson(map);
        }
    }
}
