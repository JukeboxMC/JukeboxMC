package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWeatheredCutCopperStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWeatheredCutCopperStairs extends Item{

    public ItemWeatheredCutCopperStairs() {
        super( "minecraft:weathered_cut_copper_stairs" );
    }

    @Override
    public BlockWeatheredCutCopperStairs getBlock() {
        return new BlockWeatheredCutCopperStairs();
    }
}
