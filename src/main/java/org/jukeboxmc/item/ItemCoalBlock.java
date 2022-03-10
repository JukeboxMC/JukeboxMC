package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCoal;
import org.jukeboxmc.item.type.ItemBurnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoalBlock extends Item implements ItemBurnable {

    public ItemCoalBlock() {
        super ( "minecraft:coal_block" );
    }

    @Override
    public BlockCoal getBlock() {
        return new BlockCoal();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 800000 );
    }
}
