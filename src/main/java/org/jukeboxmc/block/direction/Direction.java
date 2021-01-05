package org.jukeboxmc.block.direction;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum Direction {

    SOUTH,
    WEST,
    NORTH,
    EAST;

    public Direction opposite() {
        switch ( this ) {
            case NORTH:
                return SOUTH;
            case WEST:
                return EAST;
            case EAST:
                return WEST;
            default:
                return NORTH;
        }
    }
}
