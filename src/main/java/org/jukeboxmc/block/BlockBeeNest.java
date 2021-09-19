package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.blockentity.BlockEntityBeehive;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemBeeNest;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBeeNest extends Block {

    public BlockBeeNest() {
        super( "minecraft:bee_nest" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setDirection( player.getDirection().opposite() );
        world.setBlock( placePosition, this );

        BlockEntityType.BEEHIVE.<BlockEntityBeehive>createBlockEntity( this ).spawn();
        return true;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityBeehive blockEntityBeehive = this.getBlockEntity();
        if ( blockEntityBeehive != null ) {
            blockEntityBeehive.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public ItemBeeNest toItem() {
        return new ItemBeeNest();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BEE_NEST;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntityBeehive getBlockEntity() {
        return (BlockEntityBeehive) this.world.getBlockEntity( this.getLocation(), this.location.getDimension() );
    }

    @Override
    public double getHardness() {
        return 0.3;
    }

    @Override
    public List<Item> getDrops( Item itemInHand ) {
        return Collections.emptyList();
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
