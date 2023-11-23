package org.jukeboxmc.item.behavior;

import org.jukeboxmc.item.Burnable;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.Identifier;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPlanks extends Item implements Burnable {

    public ItemPlanks( ItemType itemType ) {
        super( itemType );
    }

    public ItemPlanks( Identifier identifier) {
        super( identifier );
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
