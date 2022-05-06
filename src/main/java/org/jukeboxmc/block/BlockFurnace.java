package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntityFurnace;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.inventory.FurnaceInventory;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemFurnace;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFurnace extends Block {

    public BlockFurnace() {
        super( "minecraft:furnace" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setBlockFace( player.getDirection().toBlockFace().opposite() );
        world.setBlock( placePosition, this , 0);

        BlockEntityType.FURNACE.<BlockEntityFurnace>createBlockEntity( this ).spawn();
        return true;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityFurnace blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public boolean onBlockBreak( Vector breakPosition ) {
        BlockEntityFurnace blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            FurnaceInventory furnaceInventory = blockEntity.getFurnaceInventory();
            for ( Item content : furnaceInventory.getContents() ) {
                if ( content != null && !content.getItemType().equals( ItemType.AIR ) ){
                     this.location.getWorld().dropItem( content, breakPosition, null ).spawn();
                }
            }
            furnaceInventory.clear();
            furnaceInventory.getViewer().clear();
        }
        return super.onBlockBreak( breakPosition );
    }

    @Override
    public ItemFurnace toItem() {
        return new ItemFurnace();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.FURNACE;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntityFurnace getBlockEntity() {
        return (BlockEntityFurnace) this.world.getBlockEntity( this.getLocation(), this.location.getDimension() );
    }

    @Override
    public double getHardness() {
        return 3.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
