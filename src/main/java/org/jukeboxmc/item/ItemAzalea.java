package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAzalea;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAzalea extends Item implements Burnable {

    public ItemAzalea() {
        super( "minecraft:azalea" );
    }

    @Override
    public BlockAzalea getBlock() {
        return new BlockAzalea();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 100 );
    }
}
