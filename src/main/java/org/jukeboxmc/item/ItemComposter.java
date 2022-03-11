package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockComposter;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemComposter extends Item implements Burnable {

    public ItemComposter() {
        super( "minecraft:composter" );
    }

    @Override
    public BlockComposter getBlock() {
        return new BlockComposter();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
