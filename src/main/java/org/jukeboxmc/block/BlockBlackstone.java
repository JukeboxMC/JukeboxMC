package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBlackstone;
import org.jukeboxmc.item.type.ItemToolType;

public class BlockBlackstone extends Block {

    public BlockBlackstone() {
        super("minecraft:blackstone");
    }

    @Override
    public ItemBlackstone toItem() {
        return new ItemBlackstone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BLACKSTONE;
    }

    @Override
    public double getHardness() {
        return 1.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

}