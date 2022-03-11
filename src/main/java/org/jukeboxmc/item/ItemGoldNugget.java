package org.jukeboxmc.item;

import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGoldNugget extends Item implements Burnable {

    public ItemGoldNugget() {
        super ( "minecraft:gold_nugget" );
    }


    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 20 );
    }
}
