package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaDoor;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaDoor extends Item implements Burnable {

    public ItemAcaciaDoor() {
        super( "minecraft:acacia_door" );
    }

    @Override
    public BlockAcaciaDoor getBlock() {
        return new BlockAcaciaDoor();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 200 );
    }
}
