package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockExposedCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemExposedCutCopperSlab extends Item {

    public ItemExposedCutCopperSlab() {
        super( "minecraft:exposed_cut_copper_slab" );
    }

    @Override
    public BlockExposedCutCopperSlab getBlock() {
        return new BlockExposedCutCopperSlab();
    }
}
