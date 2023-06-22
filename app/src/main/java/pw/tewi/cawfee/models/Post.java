package pw.tewi.cawfee.models;

import java.util.List;

import lombok.Data;

/**
 * Representation of a single post in a thread, the catalog, etc
 * <br>
 * <a href="https://github.com/4chan/4chan-API/blob/master/pages/Threads.md">
 * GET {board}/thread/{op ID}.json
 * </a>
 */
@Data
public final class Post {
    private long no;
    private long resto;
    private int sticky;
    private int closed;
    private String now;
    private long time;
    private String name;
    private String trip;
    private String id;
    private String capcode;
    private String country;
    private String country_name;
    private String board_flag;
    private String flag_name;
    private String sub;
    private String com;
    private long tim;
    private String filename;
    private String ext;
    private int fsize;
    private String md5;
    private int w;
    private int h;
    private int tn_w;
    private int tn_h;
    private int filedeleted;
    private int spoiler;
    private int custom_spoiler;
    private int omitted_posts;
    private int omitted_images;
    private int replies;
    private int images;
    private int bumplimit;
    private int imagelimit;
    private int last_modified;
    private String tag;
    private String semantic_url;
    private int since4pass;
    private int unique_ips;
    private int m_img;
    private int archived;
    private int archived_on;
    private List<Post> last_replies;

    private String imageUrl;
    private String thumbnailUrl;
}
