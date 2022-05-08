package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCobbledDeepslateStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCobbledDeepslateStairs extends BlockStairs {

    public BlockCobbledDeepslateStairs() {
        super( "minecraft:cobbled_deepslate_stairs" );
    }

    @Override
    public ItemCobbledDeepslateStairs toItem() {
        return new ItemCobbledDeepslateStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.COBBLED_DEEPSLATE_STAIRS;
    }
}
