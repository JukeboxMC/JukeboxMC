package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSmithingTable;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSmithingTable extends Item implements Burnable {

    public ItemSmithingTable() {
        super ( "minecraft:smithing_table" );
    }

    @Override
    public BlockSmithingTable getBlock() {
        return new BlockSmithingTable();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
