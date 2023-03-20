package org.jukeboxmc.block.direction;

import org.jetbrains.annotations.Nullable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum TorchFacing {

    UNKNOWN,
    WEST,
    EAST,
    NORTH,
    SOUTH,
    TOP;

    public @Nullable BlockFace toBlockFace() {
        return switch (this) {
            case WEST -> BlockFace.WEST;
            case EAST -> BlockFace.EAST;
            case NORTH -> BlockFace.NORTH;
            case SOUTH -> BlockFace.SOUTH;
            case TOP -> BlockFace.UP;
            default -> null;
        };
    }

}
