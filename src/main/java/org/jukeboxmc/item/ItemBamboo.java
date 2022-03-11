package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBamboo;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBamboo extends Item implements Burnable {

    public ItemBamboo() {
        super ( "minecraft:bamboo" );
    }

    @Override
    public BlockBamboo getBlock() {
        return new BlockBamboo();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 50 );
    }
}
