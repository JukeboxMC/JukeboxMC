package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemExposedCutCopperStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockExposedCutCopperStairs extends BlockStairs {

    public BlockExposedCutCopperStairs() {
        super( "minecraft:exposed_cut_copper_stairs" );
    }

    @Override
    public ItemExposedCutCopperStairs toItem() {
        return new ItemExposedCutCopperStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.EXPOSED_CUT_COPPER_STAIRS;
    }
}
