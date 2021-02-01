package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.CrossDirection;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStairs extends Block {

    public BlockStairs( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setCrossDirection( player.getDirection().toCrossDirection() );

        if ( ( clickedPosition.getY() > 0.5 && blockFace != BlockFace.UP ) || blockFace == BlockFace.DOWN ) {
            this.setUpsideDown( true );
        }

        world.setBlock( placePosition, this );
        return true;
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
