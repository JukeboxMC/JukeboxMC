package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.Direction;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSpruceDoor extends Block {

    public BlockSpruceDoor() {
        super( "minecraft:spruce_door" );
    }

    public void setOpen( boolean value ) {
        this.setState( "open_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isOpen() {
        return this.stateExists( "open_bit" ) && this.getByteState( "open_bit" ) == 1;
    }

    public void setUpperBlock( boolean value ) {
        this.setState( "upper_block_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpperBlock( boolean value ) {
        return this.stateExists( "upper_block_bit" ) && this.getByteState( "upper_block_bit" ) == 1;
    }

    public void setDoorHinge( boolean value ) {
        this.setState( "door_hinge_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isDoorHinge() {
        return this.stateExists( "door_hinge_bit" ) && this.getByteState( "door_hinge_bit" ) == 1;
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
