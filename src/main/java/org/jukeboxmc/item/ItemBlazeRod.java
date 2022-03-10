package org.jukeboxmc.item;

import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlazeRod extends Item implements Burnable {

    public ItemBlazeRod() {
        super( "minecraft:blaze_rod" );
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 2400 );
    }
}
