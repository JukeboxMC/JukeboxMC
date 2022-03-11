package org.jukeboxmc.item;

import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStick extends Item implements Burnable {

    public ItemStick() {
        super ( "minecraft:stick" );
    }


    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 100 );
    }
}
