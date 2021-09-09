package jukeboxmc.block.direction;

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

    public Direction toDirection() {
        switch ( this ) {
            case NORTH:
                return Direction.NORTH;
            case EAST:
                return Direction.EAST;
            case SOUTH:
                return Direction.SOUTH;
            case WEST:
                return Direction.WEST;
            default:
                return null;
        }
    }

    public TorchFacing torchFacing() {
        switch ( this ) {
            case UP:
                return TorchFacing.TOP;
            case WEST:
                return TorchFacing.WEST;
            case EAST:
                return TorchFacing.EAST;
            case NORTH:
                return TorchFacing.NORTH;
            case SOUTH:
                return TorchFacing.SOUTH;
            default:
                return TorchFacing.UNKNOWN;
        }
    }

}
