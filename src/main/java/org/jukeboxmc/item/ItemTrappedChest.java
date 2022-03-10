package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockTrappedChest;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTrappedChest extends Item implements Burnable {

    public ItemTrappedChest() {
        super ( "minecraft:trapped_chest" );
    }

    @Override
    public BlockTrappedChest getBlock() {
        return new BlockTrappedChest();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
