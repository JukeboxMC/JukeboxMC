package org.jukeboxmc.block.data;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum LeverDirection {

    DOWN_EAST_WEST,
    EAST,
    WEST,
    SOUTH,
    NORTH,
    UP_NORTH_SOUTH,
    UP_EAST_WEST,
    DOWN_NORTH_SOUTH;

    public static LeverDirection forDirection( BlockFace blockFace, Direction playerDirection ) {
        return switch ( blockFace ) {
            case DOWN -> switch ( playerDirection ) {
                case WEST, EAST -> DOWN_EAST_WEST;
                case NORTH, SOUTH -> DOWN_NORTH_SOUTH;
            };
            case UP -> switch ( playerDirection ) {
                case WEST, EAST -> UP_EAST_WEST;
                case NORTH, SOUTH -> UP_NORTH_SOUTH;
            };
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case EAST -> EAST;
        };
    }
}
