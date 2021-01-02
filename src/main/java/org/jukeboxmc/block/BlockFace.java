package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum BlockFace {

    DOWN,
    UP,
    EAST,
    WEST,
    NORTH,
    SOUTH;

    public BlockFace opposite() {
        switch ( this ) {
            case DOWN:
                return UP;
            case UP:
                return DOWN;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            case NORTH:
                return SOUTH;
            default:
                return NORTH;
        }
    }

}
