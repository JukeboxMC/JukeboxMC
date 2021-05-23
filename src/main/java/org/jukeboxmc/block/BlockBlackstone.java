package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBlackstone;

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

}