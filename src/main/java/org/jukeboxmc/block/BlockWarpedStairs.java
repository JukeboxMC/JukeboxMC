package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWarpedStairs;

public class BlockWarpedStairs extends BlockStairs {

    public BlockWarpedStairs() {
        super("minecraft:warped_stairs");
    }

    @Override
    public ItemWarpedStairs toItem() {
        return new ItemWarpedStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.WARPED_STAIRS;
    }

}