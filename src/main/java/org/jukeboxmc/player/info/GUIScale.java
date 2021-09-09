package org.jukeboxmc.player.info;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum GUIScale {

    CLASSIC,
    POCKET;

    public static GUIScale getGUIScale( int id ) {
        switch ( id ) {
            case 0:
                return CLASSIC;
            case 1:
                return POCKET;
            default:
                return null;
        }
    }

}
