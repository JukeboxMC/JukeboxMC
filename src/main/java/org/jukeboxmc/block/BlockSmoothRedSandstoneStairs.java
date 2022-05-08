package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSmoothRedSandstoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSmoothRedSandstoneStairs extends BlockStairs {

    public BlockSmoothRedSandstoneStairs() {
        super( "minecraft:smooth_red_sandstone_stairs" );
    }

    @Override
    public ItemSmoothRedSandstoneStairs toItem() {
        return new ItemSmoothRedSandstoneStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.SMOOTH_RED_SANDSTONE_STAIRS;
    }

}
