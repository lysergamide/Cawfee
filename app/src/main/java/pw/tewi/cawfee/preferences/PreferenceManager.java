package pw.tewi.cawfee.preferences;


/**
 * Responsible for setting and fetching app preferences
 */
public interface PreferenceManager {
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
    PreferenceManager theme(String themeName);

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
    PreferenceManager lastBoard(String boardName);

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
    PreferenceManager lastWebsite(String siteName);
}
