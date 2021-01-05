package org.jukeboxmc.block.direction;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum BlockFace {

    DOWN,
    UP,
    NORTH,
    SOUTH,
    WEST,
    EAST;

    public BlockFace opposite() {
        switch ( this ) {
            case DOWN:
                return UP;
            case UP:
                return DOWN;
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
