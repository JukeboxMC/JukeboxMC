package org.jukeboxmc.item;

import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBowl extends Item implements Burnable {

    public ItemBowl() {
        super ( "minecraft:bowl" );
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 200 );
    }
}
