package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSmoothSandstoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSmoothSandstoneStairs extends BlockStairs {

    public BlockSmoothSandstoneStairs() {
        super( "minecraft:smooth_sandstone_stairs" );
    }

    @Override
    public ItemSmoothSandstoneStairs toItem() {
        return new ItemSmoothSandstoneStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SMOOTH_SANDSTONE_STAIRS;
    }

}
