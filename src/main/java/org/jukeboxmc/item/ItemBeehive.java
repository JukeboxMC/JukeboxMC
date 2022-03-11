package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBeehive;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBeehive extends Item implements Burnable {

    public ItemBeehive() {
        super ( "minecraft:beehive" );
    }

    @Override
    public BlockBeehive getBlock() {
        return new BlockBeehive();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
