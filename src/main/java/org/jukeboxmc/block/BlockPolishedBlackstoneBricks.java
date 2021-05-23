package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPolishedBlackstoneBricks;

public class BlockPolishedBlackstoneBricks extends Block {

    public BlockPolishedBlackstoneBricks() {
        super("minecraft:polished_blackstone_bricks");
    }

    @Override
    public ItemPolishedBlackstoneBricks toItem() {
        return new ItemPolishedBlackstoneBricks();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POLISHED_BLACKSTONE_BRICKS;
    }

}