package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemRepeater;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRepeater extends BlockWaterlogable {

    public BlockRepeater() {
        super( "minecraft:unpowered_repeater" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setDirection( player.getDirection().opposite() );
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        int delay = this.getRepeaterDelay();

        if ( delay != 3 ) {
            this.setRepeaterDelay( delay + 1 );
        } else {
            this.setRepeaterDelay( 0 );
        }
        return true;
    }

    @Override
    public ItemRepeater toItem() {
        return new ItemRepeater();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.UNPOWERED_REPEATER;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setRepeaterDelay( int value ) { //0-3
        this.setState( "repeater_delay", value );
    }

    public int getRepeaterDelay() {
        return this.stateExists( "repeater_delay" ) ? this.getIntState( "repeater_delay" ) : 0;
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
