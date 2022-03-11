package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedBirchLog;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedBirchLog extends Item implements Burnable {

    public ItemStrippedBirchLog() {
        super ( "minecraft:stripped_birch_log" );
    }

    @Override
    public BlockStrippedBirchLog getBlock() {
        return new BlockStrippedBirchLog();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
