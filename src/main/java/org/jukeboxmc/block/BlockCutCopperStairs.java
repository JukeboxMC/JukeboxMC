package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemCutCopperStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCutCopperStairs extends BlockStairs {

    public BlockCutCopperStairs() {
        super( "minecraft:cut_copper_stairs" );
    }

    @Override
    public ItemCutCopperStairs toItem() {
        return new ItemCutCopperStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CUT_COPPER_STAIRS;
    }
}
