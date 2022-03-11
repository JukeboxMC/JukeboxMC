package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJukebox;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJukebox extends Item implements Burnable {

    public ItemJukebox() {
        super ( "minecraft:jukebox" );
    }

    @Override
    public BlockJukebox getBlock() {
        return new BlockJukebox();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
