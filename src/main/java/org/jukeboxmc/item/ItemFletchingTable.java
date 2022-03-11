package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockFletchingTable;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFletchingTable extends Item implements Burnable {

    public ItemFletchingTable() {
        super ( "minecraft:fletching_table" );
    }

    @Override
    public BlockFletchingTable getBlock() {
        return new BlockFletchingTable();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
