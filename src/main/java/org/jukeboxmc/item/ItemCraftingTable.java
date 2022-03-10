package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCraftingTable;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCraftingTable extends Item implements Burnable {

    public ItemCraftingTable() {
        super( "minecraft:crafting_table" );
    }

    @Override
    public BlockCraftingTable getBlock() {
        return new BlockCraftingTable();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
