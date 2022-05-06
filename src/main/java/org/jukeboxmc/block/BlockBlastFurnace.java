package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntityBlastFurnace;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.inventory.BlastFurnaceInventory;
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
public class BlockBlastFurnace extends Block {

    public BlockBlastFurnace() {
        super( "minecraft:blast_furnace" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setBlockFace( player.getDirection().toBlockFace().opposite() );
        world.setBlock( placePosition, this, 0 );
        BlockEntityType.BLAST_FURNACE.<BlockEntityBlastFurnace>createBlockEntity( this ).spawn();
        return true;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityBlastFurnace blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public boolean onBlockBreak( Vector breakPosition ) {
        BlockEntityBlastFurnace blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            BlastFurnaceInventory furnaceInventory = blockEntity.getBlastFurnaceInventory();
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
        return BlockType.BLAST_FURNACE;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntityBlastFurnace getBlockEntity() {
        return (BlockEntityBlastFurnace) this.world.getBlockEntity( this.getLocation(), this.location.getDimension() );
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
