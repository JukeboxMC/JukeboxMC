package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBeeNest;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBeeNest extends Item implements Burnable {

    public ItemBeeNest() {
        super ( "minecraft:bee_nest" );
    }

    @Override
    public BlockBeeNest getBlock() {
        return new BlockBeeNest();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
