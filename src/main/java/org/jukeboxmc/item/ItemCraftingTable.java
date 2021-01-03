package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCraftingTable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCraftingTable extends Item {

    public ItemCraftingTable() {
        super( "minecraft:crafting_table", 58 );
    }

    @Override
    public Block getBlock() {
        return new BlockCraftingTable();
    }
}
