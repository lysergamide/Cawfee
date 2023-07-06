package pw.tewi.cawfee.ui.preferences;


import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import lombok.NonNull;

public class ChanPreferenceManager implements CawfeePreferenceManager {
    public static final String NAME                = "CawfeePreferences";
    public static final String THEME               = "LastWebSite";
    public static final String LAST_SITE           = "LastWebSite";
    public static final String LAST_BOARD          = "LastBoard";
    public static final String CATALOG_LAYOUT      = "CatalogLayout";
    public static final String CATALOG_SORT_METHOD = "CatalogSortMethod";
    public static final String ENABLED_SUFFIX      = "_enabled";

    public final SharedPreferences prefs;

    @Inject
    public ChanPreferenceManager(@NonNull @ApplicationContext Context ctx) {
        prefs = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    private CawfeePreferenceManager putString(String key, String value) {
        prefs.edit().putString(key, value).apply();

        return this;
    }

    private CawfeePreferenceManager putBool(String key, boolean state) {
        prefs.edit().putBoolean(key, state).apply();

        return this;
    }

    private CawfeePreferenceManager enableKey(String key) { return putBool(key + ENABLED_SUFFIX, true); }
    private CawfeePreferenceManager disableKey(String key) { return putBool(key + ENABLED_SUFFIX, false); }

    @Override public String theme() { return prefs.getString(THEME, ""); }
    @Override public CawfeePreferenceManager theme(String themeName) { return putString(THEME, themeName); }
    @Override public String lastBoard() { return prefs.getString(LAST_SITE, ""); }
    @Override public CawfeePreferenceManager lastBoard(String boardName) { return putString(LAST_BOARD, boardName); }
    @Override public String lastWebsite() { return prefs.getString(LAST_SITE, ""); }
    @Override public CawfeePreferenceManager lastWebsite(String siteName) { return putString(LAST_SITE, siteName); }
    @Override public CawfeePreferenceManager enableBoard(String board) { return enableKey(board); }
    @Override public CawfeePreferenceManager disableBoard(String board) { return disableKey(board); }
    @Override public CawfeePreferenceManager enableSite(String site) { return enableKey(site); }
    @Override public CawfeePreferenceManager disableSite(String site) { return disableKey(site); }

    @Override
    public Boolean isBoardEnabled(String board) {
        return prefs.getBoolean(board + ENABLED_SUFFIX, false);
    }

    @Override
    public Boolean isSiteEnabled(String site) {
        return prefs.getBoolean(site + ENABLED_SUFFIX, false);
    }
}
