package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedOakLog;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedOakLog extends Item implements Burnable {

    public ItemStrippedOakLog() {
        super ( "minecraft:stripped_oak_log" );
    }

    @Override
    public BlockStrippedOakLog getBlock() {
        return new BlockStrippedOakLog();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
