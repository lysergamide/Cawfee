package pw.tewi.cawfee.preferences;


import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import lombok.NonNull;

public class ChanPreferenceManager implements PreferenceManager {
    public static final String NAME = "CawfeePreferences";
    public static final String THEME = "LastWebSite";
    public static final String LAST_SITE = "LastWebSite";
    public static final String LAST_BOARD = "LastBoard";
    public static final String CATALOG_LAYOUT = "CatalogLayout";
    public static final String CATALOG_SORT_METHOD = "BoardSortMethod";

    public final SharedPreferences prefs;

    @Inject
    public ChanPreferenceManager(@NonNull @ApplicationContext Context ctx) {
        prefs = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    private PreferenceManager putString(String key, String defaultValue) {
        prefs.edit().putString(key, defaultValue).apply();

        return this;
    }

    @Override public String theme() { return prefs.getString(THEME, ""); }
    @Override public PreferenceManager theme(String themeName) { return putString(THEME, themeName); }
    @Override public String lastBoard() { return prefs.getString(LAST_SITE, ""); }
    @Override public PreferenceManager lastBoard(String boardName) { return putString(LAST_BOARD, boardName); }
    @Override public String lastWebsite() { return prefs.getString(LAST_SITE, ""); }
    @Override public PreferenceManager lastWebsite(String siteName) { return putString(LAST_SITE, siteName); }
}
