package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockWeatheredDoubleCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWeatheredDoubleCutCopperSlab extends Item{

    public ItemWeatheredDoubleCutCopperSlab() {
        super( "minecraft:weathered_double_cut_copper_slab" );
    }

    @Override
    public BlockWeatheredDoubleCutCopperSlab getBlock() {
        return new BlockWeatheredDoubleCutCopperSlab();
    }
}
