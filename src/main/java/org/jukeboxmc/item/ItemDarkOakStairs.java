package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDarkOakStairs;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakStairs extends Item implements Burnable {

    public ItemDarkOakStairs() {
        super ( "minecraft:dark_oak_stairs" );
    }

    @Override
    public BlockDarkOakStairs getBlock() {
        return new BlockDarkOakStairs();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
