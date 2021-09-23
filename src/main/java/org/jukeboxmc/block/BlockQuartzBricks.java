package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemQuartzBricks;
import org.jukeboxmc.item.type.ItemToolType;

public class BlockQuartzBricks extends Block {

    public BlockQuartzBricks() {
        super("minecraft:quartz_bricks");
    }

    @Override
    public ItemQuartzBricks toItem() {
        return new ItemQuartzBricks();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.QUARTZ_BRICKS;
    }

    @Override
    public double getHardness() {
        return 0.8;
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