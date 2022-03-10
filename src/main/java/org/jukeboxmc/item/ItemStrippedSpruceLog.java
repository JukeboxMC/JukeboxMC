package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedSpruceLog;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedSpruceLog extends Item implements Burnable {

    public ItemStrippedSpruceLog() {
        super ( "minecraft:stripped_spruce_log" );
    }

    @Override
    public BlockStrippedSpruceLog getBlock() {
        return new BlockStrippedSpruceLog();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
