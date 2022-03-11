package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCartographyTable;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCartographyTable extends Item implements Burnable {

    public ItemCartographyTable() {
        super ( "minecraft:cartography_table" );
    }

    @Override
    public BlockCartographyTable getBlock() {
        return new BlockCartographyTable();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
