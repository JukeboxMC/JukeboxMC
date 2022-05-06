package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.blockentity.BlockEntityLoom;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemLoom;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLoom extends Block {

    public BlockLoom() {
        super( "minecraft:loom" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setDirection( player.getDirection().opposite() );
        world.setBlock( placePosition, this, 0 );
        BlockEntityType.LOOM.<BlockEntityLoom>createBlockEntity( this ).spawn();
        return true;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityLoom blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public ItemLoom toItem() {
        return new ItemLoom();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LOOM;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntityLoom getBlockEntity() {
        return (BlockEntityLoom) this.world.getBlockEntity( this.location, this.location.getDimension() );
    }

    @Override
    public double getHardness() {
        return 2.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
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
