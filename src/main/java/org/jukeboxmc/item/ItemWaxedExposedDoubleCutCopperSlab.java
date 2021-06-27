package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockWaxedExposedDoubleCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedExposedDoubleCutCopperSlab extends Item{

    public ItemWaxedExposedDoubleCutCopperSlab() {
        super( "minecraft:waxed_exposed_double_cut_copper_slab" );
    }

    @Override
    public BlockWaxedExposedDoubleCutCopperSlab getBlock() {
        return new BlockWaxedExposedDoubleCutCopperSlab();
    }
}
