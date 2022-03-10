package org.jukeboxmc.item;

import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCharcoal extends Item implements Burnable {

    public ItemCharcoal() {
        super ( "minecraft:charcoal" );
    }


    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 1600 );
    }
}
