package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockExposedCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemExposedCutCopper extends Item{

    public ItemExposedCutCopper() {
        super( "minecraft:exposed_cut_copper" );
    }

    @Override
    public BlockExposedCutCopper getBlock() {
        return new BlockExposedCutCopper();
    }
}
