package org.jukeboxmc.player.info;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum UIProfile {

    CLASSIC,
    POCKET;

    public static UIProfile getUIProfile( int id ) {
        if ( id == 1 ) {
            return POCKET;
        }
        return CLASSIC;
    }

}
