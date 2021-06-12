package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCraftingTable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCraftingTable extends Item {

    public ItemCraftingTable() {
        super( "minecraft:crafting_table" );
    }

    @Override
    public BlockCraftingTable getBlock() {
        return new BlockCraftingTable();
    }
}
