package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLadder;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLadder extends Item implements Burnable {

    public ItemLadder() {
        super ( "minecraft:ladder" );
    }

    @Override
    public BlockLadder getBlock() {
        return new BlockLadder();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
