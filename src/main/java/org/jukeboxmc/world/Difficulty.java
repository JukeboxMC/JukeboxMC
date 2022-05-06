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
        return switch ( value ) {
            case 0 -> Difficulty.PEACEFUL;
            case 1 -> Difficulty.EASY;
            case 2 -> Difficulty.NORMAL;
            default -> Difficulty.HARD;
        };
    }
}
