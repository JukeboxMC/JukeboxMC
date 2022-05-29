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
        return switch ( this ) {
            case DOWN -> UP;
            case UP -> DOWN;
            case NORTH -> SOUTH;
            case WEST -> EAST;
            case EAST -> WEST;
            default -> NORTH;
        };
    }

    public Direction toDirection() {
        return switch ( this ) {
            case NORTH -> Direction.NORTH;
            case EAST -> Direction.EAST;
            case SOUTH -> Direction.SOUTH;
            case WEST -> Direction.WEST;
            default -> null;
        };
    }

    public TorchFacing torchFacing() {
        return switch ( this ) {
            case UP -> TorchFacing.TOP;
            case WEST -> TorchFacing.WEST;
            case EAST -> TorchFacing.EAST;
            case NORTH -> TorchFacing.NORTH;
            case SOUTH -> TorchFacing.SOUTH;
            default -> TorchFacing.UNKNOWN;
        };
    }

    public static BlockFace fromId( int value ) {
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

}
