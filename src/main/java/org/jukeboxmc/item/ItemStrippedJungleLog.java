package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedJungleLog;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedJungleLog extends Item implements Burnable {

    public ItemStrippedJungleLog() {
        super ( "minecraft:stripped_jungle_log" );
    }

    @Override
    public BlockStrippedJungleLog getBlock() {
        return new BlockStrippedJungleLog();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
