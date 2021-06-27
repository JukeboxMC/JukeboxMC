package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockExposedCutCopperStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemExposedCutCopperStairs extends Item {

    public ItemExposedCutCopperStairs() {
        super( "minecraft:exposed_cut_copper_stairs" );
    }

    @Override
    public BlockExposedCutCopperStairs getBlock() {
        return new BlockExposedCutCopperStairs();
    }
}
