package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSpruceStairs;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSpruceStairs extends Item implements Burnable {

    public ItemSpruceStairs() {
        super ( "minecraft:spruce_stairs" );
    }

    @Override
    public BlockSpruceStairs getBlock() {
        return new BlockSpruceStairs();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
