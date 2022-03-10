package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWoodenTrapdoor;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTrapdoor extends Item implements Burnable {

    public ItemTrapdoor() {
        super ( "minecraft:trapdoor" );
    }

    @Override
    public BlockWoodenTrapdoor getBlock() {
        return new BlockWoodenTrapdoor();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
