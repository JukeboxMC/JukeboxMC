package org.jukeboxmc.item;

import org.jukeboxmc.item.type.ItemBurnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoal extends Item implements ItemBurnable {

    public ItemCoal() {
        super ( "minecraft:coal" );
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 80000 );
    }
}
