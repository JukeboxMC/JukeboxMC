package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBarrel;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBarrel extends Item implements Burnable {

    public ItemBarrel() {
        super ( "minecraft:barrel" );
    }

    @Override
    public BlockBarrel getBlock() {
        return new BlockBarrel();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
