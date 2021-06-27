package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWaxedWeatheredCutCopperStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedWeatheredCutCopperStairs extends BlockStairs {
    
    public BlockWaxedWeatheredCutCopperStairs() {
        super( "minecraft:waxed_weathered_cut_copper_stairs" );
    }

    @Override
    public ItemWaxedWeatheredCutCopperStairs toItem() {
        return new ItemWaxedWeatheredCutCopperStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_WEATHERED_CUT_COPPER_STAIRS;
    }
}
