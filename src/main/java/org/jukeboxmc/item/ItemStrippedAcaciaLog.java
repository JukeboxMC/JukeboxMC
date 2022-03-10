package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedAcaciaLog;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedAcaciaLog extends Item implements Burnable {

    public ItemStrippedAcaciaLog() {
        super ( "minecraft:stripped_acacia_log" );
    }

    @Override
    public BlockStrippedAcaciaLog getBlock() {
        return new BlockStrippedAcaciaLog();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
