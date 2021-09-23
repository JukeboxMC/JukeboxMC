package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPolishedBlackstoneBricks;
import org.jukeboxmc.item.type.ItemToolType;

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

    @Override
    public double getHardness() {
        return 1.5;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

}