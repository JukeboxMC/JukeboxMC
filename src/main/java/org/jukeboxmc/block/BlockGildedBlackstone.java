package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGlidedBlackstone;
import org.jukeboxmc.item.type.ItemToolType;

public class BlockGildedBlackstone extends Block {

    public BlockGildedBlackstone() {
        super("minecraft:gilded_blackstone");
    }

    @Override
    public ItemGlidedBlackstone toItem() {
        return new ItemGlidedBlackstone();
    }

    @Override
    public BlockType getType() {
        return BlockType.GLIDED_BLACKSTONE;
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