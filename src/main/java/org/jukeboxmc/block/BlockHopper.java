package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHopper extends Block {

    public BlockHopper() {
        super( "minecraft:hopper" );
    }

    public void setToggle( boolean value ) {
        this.setState( "toggle_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isToggle() {
        return this.stateExists( "toggle_bit" ) && this.getByteState( "toggle_bit" ) == 1;
    }

    public void setBlockFace( BlockFace blockFace ) {
        switch ( blockFace ) {
            case DOWN:
                this.setState( "facing_direction", 0 );
                break;
            case UP:
                this.setState( "facing_direction", 1 );
                break;
            case NORTH:
                this.setState( "facing_direction", 2 );
                break;
            case SOUTH:
                this.setState( "facing_direction", 3 );
                break;
            case WEST:
                this.setState( "facing_direction", 4 );
                break;
            case EAST:
                this.setState( "facing_direction", 5 );
                break;
            default:
                break;
        }
    }

    public BlockFace getBlockFace() {
        int value = this.stateExists( "facing_direction" ) ? this.getIntState( "facing_direction" ) : 2;
        switch ( value ) {
            case 0:
                return BlockFace.DOWN;
            case 1:
                return BlockFace.UP;
            case 2:
                return BlockFace.NORTH;
            case 3:
                return BlockFace.SOUTH;
            case 4:
                return BlockFace.WEST;
            default:
                return BlockFace.EAST;
        }
    }
}
