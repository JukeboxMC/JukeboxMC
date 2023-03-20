package org.jukeboxmc.block.direction;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jukeboxmc.math.Vector;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum BlockFace {

    DOWN( -1, new Vector( 0, -1, 0 )),
    UP( -1, new Vector( 0, 1, 0 )),
    NORTH( 2, new Vector( 0, 0,-1 )),
    SOUTH( 0, new Vector( 0, 0, 1 )),
    WEST( 1, new Vector( -1, 0,0 )),
    EAST( 3, new Vector( 1, 0, 0 ));

    private final int horizontalIndex;
    private final Vector offset;

    BlockFace( int horizontalIndex, Vector offset ) {
        this.horizontalIndex = horizontalIndex;
        this.offset = offset;
    }

    public Vector getOffset() {
        return offset;
    }

    public int getHorizontalIndex() {
        return horizontalIndex;
    }

    public @NotNull BlockFace opposite() {
        return switch ( this ) {
            case DOWN -> UP;
            case UP -> DOWN;
            case NORTH -> SOUTH;
            case WEST -> EAST;
            case EAST -> WEST;
            default -> NORTH;
        };
    }

    public @Nullable Direction toDirection() {
        return switch ( this ) {
            case NORTH -> Direction.NORTH;
            case EAST -> Direction.EAST;
            case SOUTH -> Direction.SOUTH;
            case WEST -> Direction.WEST;
            default -> null;
        };
    }

    public @NotNull TorchFacing torchFacing() {
        return switch ( this ) {
            case UP -> TorchFacing.TOP;
            case WEST -> TorchFacing.WEST;
            case EAST -> TorchFacing.EAST;
            case NORTH -> TorchFacing.NORTH;
            case SOUTH -> TorchFacing.SOUTH;
            default -> TorchFacing.UNKNOWN;
        };
    }

    public static @Nullable BlockFace fromId(int value ) {
        return switch ( value ) {
            case 0 -> BlockFace.DOWN;
            case 1 -> BlockFace.UP;
            case 2 -> BlockFace.NORTH;
            case 3 -> BlockFace.SOUTH;
            case 4 -> BlockFace.WEST;
            case 5 -> BlockFace.EAST;
            default -> null;
        };
    }

    public static BlockFace @NotNull [] getHorizontal() {
        return new BlockFace[]{NORTH, EAST, SOUTH, WEST};
    }

}
