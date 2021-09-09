package org.jukeboxmc.world;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum Difficulty {

    PEACEFUL,
    EASY,
    NORMAL,
    HARD;

    public static Difficulty getDifficulty( int value ) {
        switch ( value ) {
            case 0:
                return Difficulty.PEACEFUL;
            case 1:
                return Difficulty.EASY;
            case 2:
                return Difficulty.NORMAL;
            default:
                return Difficulty.HARD;
        }
    }
}
