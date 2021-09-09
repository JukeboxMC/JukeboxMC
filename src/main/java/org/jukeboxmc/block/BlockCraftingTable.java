package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCraftingTable;

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
    public BlockType getBlockType() {
        return BlockType.CRAFTING_TABLE;
    }

}
