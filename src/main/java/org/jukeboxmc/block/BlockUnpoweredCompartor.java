package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.Direction;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockUnpoweredCompartor extends Block {

    public BlockUnpoweredCompartor() {
        super( "minecraft:unpowered_comparator" );
    }

    public void setOutputSubstract( boolean value ) {
        this.setState( "output_subtract_bit", value ? (byte) 1: (byte) 0 );
    }

    public boolean isOutputSubstract() {
        return this.stateExists( "output_subtract_bit" ) && this.getByteState( "output_subtract_bit" ) == 1;
    }

    public void setOutputLit( boolean value ) {
        this.setState( "output_lit_bit", value ? (byte) 1: (byte) 0 );
    }

    public boolean isOutputLit() {
        return this.stateExists( "output_lit_bit" ) && this.getByteState( "output_lit_bit" ) == 1;
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
