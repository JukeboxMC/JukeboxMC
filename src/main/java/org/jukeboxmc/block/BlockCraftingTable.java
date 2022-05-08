package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemCraftingTable;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCraftingTable extends Block {

    public BlockCraftingTable() {
        super( "minecraft:crafting_table" );
    }

    @Override
    public ItemCraftingTable toItem() {
        return new ItemCraftingTable();
    }

    @Override
    public BlockType getType() {
        return BlockType.CRAFTING_TABLE;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( player.getCraftingTableInventory(), blockPosition );
        return true;
    }

    @Override
    public double getHardness() {
        return 2.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }
}
