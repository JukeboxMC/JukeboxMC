package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntityEnchantmentTable;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemEnchantingTable;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEnchantingTable extends BlockWaterlogable {

    public BlockEnchantingTable() {
        super( "minecraft:enchanting_table" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        boolean value = super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        if ( value ) {
            BlockEntityType.ENCHANTMENT_TABLE.<BlockEntityEnchantmentTable>createBlockEntity( this ).spawn();
        }
        return value;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityEnchantmentTable blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public ItemEnchantingTable toItem() {
        return new ItemEnchantingTable();
    }

    @Override
    public BlockType getType() {
        return BlockType.ENCHANTING_TABLE;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntityEnchantmentTable getBlockEntity() {
        return (BlockEntityEnchantmentTable) this.world.getBlockEntity( this.location, this.location.getDimension() );
    }

    @Override
    public double getHardness() {
        return 5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}
