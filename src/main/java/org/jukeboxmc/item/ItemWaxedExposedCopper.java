package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWaxedExposedCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedExposedCopper extends Item{

    public ItemWaxedExposedCopper() {
        super( "minecraft:waxed_exposed_copper" );
    }

    @Override
    public BlockWaxedExposedCopper getBlock() {
        return new BlockWaxedExposedCopper();
    }
}
