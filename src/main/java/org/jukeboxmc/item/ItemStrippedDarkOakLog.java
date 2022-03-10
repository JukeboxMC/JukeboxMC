package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedDarkOakLog;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedDarkOakLog extends Item implements Burnable {

    public ItemStrippedDarkOakLog() {
        super ( "minecraft:stripped_dark_oak_log" );
    }

    @Override
    public BlockStrippedDarkOakLog getBlock() {
        return new BlockStrippedDarkOakLog();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
