package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockOakStairs;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemOakStairs extends Item implements Burnable {

    public ItemOakStairs() {
        super ( "minecraft:oak_stairs" );
    }

    @Override
    public BlockOakStairs getBlock() {
        return new BlockOakStairs();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
