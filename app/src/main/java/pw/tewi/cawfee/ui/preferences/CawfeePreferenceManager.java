package pw.tewi.cawfee.ui.preferences;


/**
 * Responsible for setting and fetching app preferences
 */
public interface CawfeePreferenceManager {
    /**
     * @return The apps current theme
     */
    String theme();
    /**
     * Change the current theme
     *
     * @param themeName the name of the new theme
     * @return this
     */
    CawfeePreferenceManager theme(String themeName);

    /**
     * @return The name of the last board that the user visited
     */
    String lastBoard();
    /**
     * Update the name of the last board visited
     *
     * @param boardName the board that was switched to
     * @return this
     */
    CawfeePreferenceManager lastBoard(String boardName);

    /**
     * @return the name of the last site used
     */
    String lastWebsite();
    /**
     * update the name of the last used site
     *
     * @param siteName the site that was switched to
     * @return this
     */
    CawfeePreferenceManager lastWebsite(String siteName);

    /**
     * @param board the name of the board to set to enabled
     * @return this
     */
    CawfeePreferenceManager enableBoard(String board);

    /**
     * @param board the name of the board to set to disabled
     * @return this
     */
    CawfeePreferenceManager disableBoard(String board);
    CawfeePreferenceManager enableSite(String site);
    CawfeePreferenceManager disableSite(String site);

    /**
     * @param board the name of the board
     * @return true if the board is enabled, false otherwise
     */
    Boolean isBoardEnabled(String board);
    Boolean isSiteEnabled(String site);
}
