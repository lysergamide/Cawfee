package pw.tewi.cawfee.api;

import com.google.gson.Gson;

import javax.inject.Inject;


public class FourChan extends ChanImageBoard {
    public static final String API    = "https://a.4cdn.org";
    public static final String IMAGE  = "https://i.4cdn.org";
    public static final String STATIC = "https://s.4cdn.org";
    public static final String NAME   = "4chan";

    @Inject public FourChan(Gson gson) { super(API, IMAGE, STATIC, NAME, gson); }
}
