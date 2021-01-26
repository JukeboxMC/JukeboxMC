package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.blockentity.BlockEntityBeehive;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBeehive extends Block {

    public BlockBeehive() {
        super( "minecraft:beehive" );
        this.setHoneyLevel( 0 );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setDirection( player.getDirection().opposite() );
        world.setBlock( placePosition, this );
    }

    @Override
    public boolean interact( Player player, BlockPosition blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityBeehive blockEntityBeehive = (BlockEntityBeehive) this.getBlockEntity();
        if ( blockEntityBeehive != null ) {
            blockEntityBeehive.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntityBeehive getBlockEntity() {
        BlockEntityBeehive blockEntity = (BlockEntityBeehive) this.world.getBlockEntity( this.getBlockPosition() );
        if ( blockEntity == null ) {
            return new BlockEntityBeehive( this );
        }
        return blockEntity;
    }

    public void setHoneyLevel( int value ) { //0-5
        this.setState( "honey_level", value );
    }

    public int getHoneyLevel() {
        return this.stateExists( "honey_level" ) ? this.getIntState( "honey_level" ) : 0;
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
