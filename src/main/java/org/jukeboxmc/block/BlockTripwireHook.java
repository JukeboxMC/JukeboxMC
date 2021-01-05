package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.Direction;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockTripwireHook extends Block {

    public BlockTripwireHook() {
        super( "minecraft:tripwire_hook" );
    }

    public void setPowered( boolean value ) {
        this.setState( "powered_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isPowered() {
        return this.stateExists( "powered_bit" ) && this.getByteState( "powered_bit" ) == 1;
    }

    public void setAttached( boolean value ) {
        this.setState( "attached_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isAttached() {
        return this.stateExists( "attached_bit" ) && this.getByteState( "attached_bit" ) == 1;
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
