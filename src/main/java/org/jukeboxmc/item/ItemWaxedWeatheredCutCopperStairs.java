package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockWaxedWeatheredCutCopperStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedWeatheredCutCopperStairs extends Item{

    public ItemWaxedWeatheredCutCopperStairs() {
        super( "minecraft:waxed_weathered_cut_copper_stairs" );
    }

    @Override
    public Block getBlock() {
        return new BlockWaxedWeatheredCutCopperStairs();
    }
}
