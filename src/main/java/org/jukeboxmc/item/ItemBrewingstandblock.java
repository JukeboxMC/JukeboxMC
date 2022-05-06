package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBrewingStand;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBrewingstandblock extends Item {

    public ItemBrewingstandblock() {
        super ( "minecraft:brewing_stand" );
    }

    @Override
    public BlockBrewingStand getBlock() {
        return new BlockBrewingStand();
    }
}
