package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPolishedBlackstoneStairs;

public class BlockPolishedBlackstoneStairs extends BlockStairs {

    public BlockPolishedBlackstoneStairs() {
        super("minecraft:polished_blackstone_stairs");
    }

    @Override
    public ItemPolishedBlackstoneStairs toItem() {
        return new ItemPolishedBlackstoneStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POLISHED_BLACKSTONE_STAIRS;
    }

}