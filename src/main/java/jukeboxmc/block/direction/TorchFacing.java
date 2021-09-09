package jukeboxmc.block.direction;

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

    public BlockFace toBlockFace() {
        switch ( this ) {
            case WEST:
                return BlockFace.WEST;
            case EAST:
                return BlockFace.EAST;
            case NORTH:
                return BlockFace.NORTH;
            case SOUTH:
                return BlockFace.SOUTH;
            case TOP:
                return BlockFace.UP;
            default:
                return null;
        }
    }

}
