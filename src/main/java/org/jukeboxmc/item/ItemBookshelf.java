package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBookshelf;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBookshelf extends Item implements Burnable {

    public ItemBookshelf() {
        super ( "minecraft:bookshelf" );
    }

    @Override
    public BlockBookshelf getBlock() {
        return new BlockBookshelf();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
