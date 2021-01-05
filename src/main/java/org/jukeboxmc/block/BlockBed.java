package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.Direction;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBed extends Block {

    public BlockBed() {
        super( "minecraft:bed" );
    }

    public void setHeadPiece( boolean value ) {
        this.setState( "head_piece_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isHeadPiece() {
        return this.stateExists( "head_piece_bit" ) && this.getByteState( "head_piece_bit" ) == 1;
    }

    public void setOccupied( boolean value ) {
        this.setState( "occupied_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isOccupied() {
        return this.stateExists( "occupied_bit" ) && this.getByteState( "occupied_bit" ) == 1;
    }

    public void setDirection( Direction direction ) {
        switch ( direction ) {
            case SOUTH:
                this.setState( "direction", 0 );
                break;
            case WEST:
                this.setState( "direction", 1 );
                break;
            case NORTH:
                this.setState( "direction", 2 );
                break;
            case EAST:
                this.setState( "direction", 3 );
                break;
        }
    }

    public Direction getDirection() {
        int value = this.stateExists( "direction" ) ? this.getIntState( "direction" ) : 0;
        switch ( value ) {
            case 0:
                return Direction.SOUTH;
            case 1:
                return Direction.WEST;
            case 2:
                return Direction.NORTH;
            default:
                return Direction.EAST;
        }
    }
}
