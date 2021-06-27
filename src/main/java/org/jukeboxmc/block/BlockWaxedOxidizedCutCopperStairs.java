package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWaxedOxidizedCutCopperStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedOxidizedCutCopperStairs extends BlockStairs {

    public BlockWaxedOxidizedCutCopperStairs() {
        super( "minecraft:waxed_oxidized_cut_copper_stairs" );
    }

    @Override
    public ItemWaxedOxidizedCutCopperStairs toItem() {
        return new ItemWaxedOxidizedCutCopperStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_OXIDIZED_CUT_COPPER_STAIRS;
    }
}
