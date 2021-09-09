package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemNetheriteBlock;

public class BlockNetheriteBlock extends Block {

    public BlockNetheriteBlock() {
        super("minecraft:netherite_block");
    }

    @Override
    public ItemNetheriteBlock toItem() {
        return new ItemNetheriteBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NETHERITE_BLOCK;
    }

}