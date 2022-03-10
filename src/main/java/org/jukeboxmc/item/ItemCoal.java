package org.jukeboxmc.item;

import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoal extends Item implements Burnable {

    public ItemCoal() {
        super ( "minecraft:coal" );
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 80000 );
    }
}
