package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWaxedExposedCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedExposedCutCopper extends Item {

    public ItemWaxedExposedCutCopper() {
        super( "minecraft:waxed_exposed_cut_copper" );
    }

    @Override
    public BlockWaxedExposedCutCopper getBlock() {
        return new BlockWaxedExposedCutCopper();
    }
}
