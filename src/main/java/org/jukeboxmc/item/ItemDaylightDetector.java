package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDaylightDetector;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDaylightDetector extends Item implements Burnable {

    public ItemDaylightDetector() {
        super ( "minecraft:daylight_detector" );
    }

    @Override
    public BlockDaylightDetector getBlock() {
        return new BlockDaylightDetector();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
