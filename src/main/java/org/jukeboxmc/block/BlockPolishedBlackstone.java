package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPolishedBlackstone;
import org.jukeboxmc.item.ItemToolType;

public class BlockPolishedBlackstone extends Block {

    public BlockPolishedBlackstone() {
        super("minecraft:polished_blackstone");
    }

    @Override
    public ItemPolishedBlackstone toItem() {
        return new ItemPolishedBlackstone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POLISHED_BLACKSTONE;
    }

    @Override
    public double getHardness() {
        return 2;
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