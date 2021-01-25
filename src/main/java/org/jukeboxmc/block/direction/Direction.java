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

    public BlockFace toBlockFace() {
        switch ( this ) {
            case SOUTH:
                return BlockFace.SOUTH;
            case WEST:
                return BlockFace.WEST;
            case NORTH:
                return BlockFace.NORTH;
            case EAST:
                return BlockFace.EAST;
            default:
                return null;
        }
    }

    public CrossDirection toCrossDirection() {
        switch ( this ) {
            case SOUTH:
                return CrossDirection.SOUTH;
            case WEST:
                return CrossDirection.WEST;
            case NORTH:
                return CrossDirection.NORTH;
            case EAST:
                return CrossDirection.EAST;
            default:
                return null;
        }
    }
}
