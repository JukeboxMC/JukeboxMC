package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJungleStairs;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJungleStairs extends Item implements Burnable {

    public ItemJungleStairs() {
        super ( "minecraft:jungle_stairs" );
    }

    @Override
    public BlockJungleStairs getBlock() {
        return new BlockJungleStairs();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
