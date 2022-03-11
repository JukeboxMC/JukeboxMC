package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaStairs;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaStairs extends Item implements Burnable {

    public ItemAcaciaStairs() {
        super( "minecraft:acacia_stairs" );
    }

    @Override
    public BlockAcaciaStairs getBlock() {
        return new BlockAcaciaStairs();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
