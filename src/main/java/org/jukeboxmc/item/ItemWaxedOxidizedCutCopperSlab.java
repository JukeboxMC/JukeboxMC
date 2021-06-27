package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWaxedOxidizedCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedOxidizedCutCopperSlab extends Item{

    public ItemWaxedOxidizedCutCopperSlab() {
        super( "minecraft:waxed_oxidized_cut_copper_slab" );
    }

    @Override
    public BlockWaxedOxidizedCutCopperSlab getBlock() {
        return new BlockWaxedOxidizedCutCopperSlab();
    }
}
