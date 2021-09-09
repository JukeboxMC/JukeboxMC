package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemChiseledPolishedBlackstone;

public class BlockChiseledPolishedBlackstone extends Block {

    public BlockChiseledPolishedBlackstone() {
        super("minecraft:chiseled_polished_blackstone");
    }

    @Override
    public ItemChiseledPolishedBlackstone toItem() {
        return new ItemChiseledPolishedBlackstone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CHISELED_POLISHED_BLACKSTONE;
    }

}