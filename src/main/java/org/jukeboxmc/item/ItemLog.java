package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLog;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.LogType;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLog extends Item implements Burnable {

    public ItemLog( int blockRuntimeId ) {
        super( "minecraft:log", blockRuntimeId );
    }

    @Override
    public BlockLog getBlock() {
        return (BlockLog) BlockType.getBlock( this.blockRuntimeId );
    }

    public LogType getLogType() {
        return this.getBlock().getLogType();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
