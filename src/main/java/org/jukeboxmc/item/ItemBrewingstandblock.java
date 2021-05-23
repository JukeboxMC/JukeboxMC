package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBrewingStand;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBrewingstandblock extends Item {

    public ItemBrewingstandblock() {
        super( 117 );
    }

    @Override
    public BlockBrewingStand getBlock() {
        return new BlockBrewingStand();
    }
}
