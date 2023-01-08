package org.jukeboxmc.block.direction;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum Direction {

    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Direction opposite() {
        return switch ( this ) {
            case NORTH -> SOUTH;
            case WEST -> EAST;
            case EAST -> WEST;
            default -> NORTH;
        };
    }

    public Direction getRightDirection() {
        return switch ( this ) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            default -> NORTH;
        };
    }

    public Direction getLeftDirection() {
        return switch ( this ) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            default -> SOUTH;
        };
    }

    public BlockFace toBlockFace() {
        return switch ( this ) {
            case SOUTH -> BlockFace.SOUTH;
            case WEST -> BlockFace.WEST;
            case NORTH -> BlockFace.NORTH;
            default -> BlockFace.EAST;
        };
    }

    public CrossDirection toCrossDirection() {
        return switch ( this ) {
            case SOUTH -> CrossDirection.SOUTH;
            case WEST -> CrossDirection.WEST;
            case NORTH -> CrossDirection.NORTH;
            default -> CrossDirection.EAST;
        };
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
