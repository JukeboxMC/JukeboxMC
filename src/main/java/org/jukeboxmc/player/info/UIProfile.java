package org.jukeboxmc.player.info;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum UIProfile {

    CLASSIC,
    POCKET;

    public static UIProfile getById( int id ) {
        return switch ( id ) {
            case 0 -> CLASSIC;
            case 1 -> POCKET;
            default -> null;
        };
    }

}
