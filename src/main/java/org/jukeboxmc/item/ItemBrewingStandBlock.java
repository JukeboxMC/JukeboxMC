package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBrewingStand;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBrewingStandBlock extends Item {

    public ItemBrewingStandBlock() {
        super( "minecraft:brewingstandblock", 117 );
    }

    @Override
    public Block getBlock() {
        return new BlockBrewingStand();
    }
}
