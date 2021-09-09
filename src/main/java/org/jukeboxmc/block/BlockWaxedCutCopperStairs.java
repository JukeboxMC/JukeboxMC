package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWaxedCutCopperStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedCutCopperStairs extends BlockStairs {

    public BlockWaxedCutCopperStairs() {
        super( "minecraft:waxed_cut_copper_stairs" );
    }

    @Override
    public ItemWaxedCutCopperStairs toItem() {
        return new ItemWaxedCutCopperStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_CUT_COPPER_STAIRS;
    }
}
