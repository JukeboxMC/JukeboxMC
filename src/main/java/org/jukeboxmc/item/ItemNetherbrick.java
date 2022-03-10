package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherBrickBlock;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherbrick extends Item implements Burnable {

    public ItemNetherbrick() {
        super ( "minecraft:nether_brick" );
    }

    @Override
    public BlockNetherBrickBlock getBlock() {
        return new BlockNetherBrickBlock();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 20 );
    }
}
