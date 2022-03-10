package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLoom;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLoom extends Item implements Burnable {

    public ItemLoom() {
        super ( "minecraft:loom" );
    }

    @Override
    public BlockLoom getBlock() {
        return new BlockLoom();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
