package jukeboxmc.block.direction;

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

    public Direction getRightDirection() {
        switch ( this ) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            default:
                return NORTH;
        }
    }

    public Direction getLeftDirection() {
        switch ( this ) {
            case NORTH:
                return WEST;
            case EAST:
                return NORTH;
            case SOUTH:
                return EAST;
            default:
                return SOUTH;
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
            default:
                return BlockFace.EAST;
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
            default:
                return CrossDirection.EAST;
        }
    }

    public static Direction fromAngle( float value ) {
        value -= 90;
        value %= 360;

        if ( value < 0 ) {
            value += 360.0;
        }

        if ( ( 0 <= value && value < 45 ) || ( 315 <= value && value < 360 ) ) {
            return Direction.NORTH;
        }

        if ( 45 <= value && value < 135 ) {
            return Direction.EAST;
        }

        if ( 135 <= value && value < 225 ) {
            return Direction.SOUTH;
        }

        return Direction.WEST;
    }
}
