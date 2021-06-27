package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockWaxedWeatheredDoubleCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedWeatheredDoubleCutCopperSlab extends Item{

    public ItemWaxedWeatheredDoubleCutCopperSlab() {
        super( "minecraft:waxed_weathered_double_cut_copper_slab" );
    }

    @Override
    public BlockWaxedWeatheredDoubleCutCopperSlab getBlock() {
        return new BlockWaxedWeatheredDoubleCutCopperSlab();
    }
}
