package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBrewingStand;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBrewingStand extends Item {

    public ItemBrewingStand() {
        super( "minecraft:brewing_stand", 429 );
    }

    @Override
    public Block getBlock() {
        return new BlockBrewingStand();
    }
}
