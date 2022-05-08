package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPolishedBlackstoneBrickStairs;

public class BlockPolishedBlackstoneBrickStairs extends BlockStairs {

    public BlockPolishedBlackstoneBrickStairs() {
        super("minecraft:polished_blackstone_brick_stairs");
    }

    @Override
    public ItemPolishedBlackstoneBrickStairs toItem() {
        return new ItemPolishedBlackstoneBrickStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.POLISHED_BLACKSTONE_BRICK_STAIRS;
    }

}