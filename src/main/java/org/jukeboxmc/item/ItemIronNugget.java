package org.jukeboxmc.item;

import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemIronNugget extends Item implements Burnable {

    public ItemIronNugget() {
        super ( "minecraft:iron_nugget" );
    }


    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 20 );
    }
}
