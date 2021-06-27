package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockWaxedOxidizedDoubleCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedOxidizedDoubleCutCopperSlab extends Item{

    public ItemWaxedOxidizedDoubleCutCopperSlab() {
        super( "minecraft:waxed_oxidized_double_cut_copper_slab" );
    }

    @Override
    public BlockWaxedOxidizedDoubleCutCopperSlab getBlock() {
        return new BlockWaxedOxidizedDoubleCutCopperSlab();
    }
}
