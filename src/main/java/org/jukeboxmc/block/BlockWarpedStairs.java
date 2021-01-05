package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.CrossDirection;

public class BlockWarpedStairs extends Block {

    public BlockWarpedStairs() {
        super("minecraft:warped_stairs");
    }

    public void setUpsideDown( boolean value ) {
        this.setState( "upside_down_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpsideDown() {
        return this.stateExists( "upside_down_bit" ) && this.getByteState( "upside_down_bit" ) == 1;
    }

    public void setCrossDirection( CrossDirection crossDirection ) {
        switch ( crossDirection ) {
            case EAST:
                this.setState( "weirdo_direction", 0 );
                break;
            case WEST:
                this.setState( "weirdo_direction", 1 );
                break;
            case SOUTH:
                this.setState( "weirdo_direction", 2 );
                break;
            default:
                this.setState( "weirdo_direction", 3 );
                break;
        }
    }

    public CrossDirection getCrossDirection() {
        int value = this.stateExists( "weirdo_direction" ) ? this.getIntState( "weirdo_direction" ) : 0;
        switch ( value ) {
            case 0:
                return CrossDirection.EAST;
            case 1:
                return CrossDirection.WEST;
            case 2:
                return CrossDirection.SOUTH;
            default:
                return CrossDirection.NORTH;
        }
    }
}