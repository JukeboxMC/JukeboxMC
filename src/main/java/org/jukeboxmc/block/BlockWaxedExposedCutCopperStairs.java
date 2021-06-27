package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWaxedExposedCutCopperStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedExposedCutCopperStairs extends BlockStairs {

    public BlockWaxedExposedCutCopperStairs() {
        super( "minecraft:waxed_exposed_cut_copper_stairs" );
    }

    @Override
    public ItemWaxedExposedCutCopperStairs toItem() {
        return new ItemWaxedExposedCutCopperStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_EXPOSED_CUT_COPPER_STAIRS;
    }
}
