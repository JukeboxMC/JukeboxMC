package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPolishedBlackstone;

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

}