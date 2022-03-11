package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLectern;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLectern extends Item implements Burnable {

    public ItemLectern() {
        super ( "minecraft:lectern" );
    }

    @Override
    public BlockLectern getBlock() {
        return new BlockLectern();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
