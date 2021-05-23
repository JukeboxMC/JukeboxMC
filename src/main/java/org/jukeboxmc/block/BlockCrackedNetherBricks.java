package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCrackedNetherBricks;

public class BlockCrackedNetherBricks extends Block {

    public BlockCrackedNetherBricks() {
        super("minecraft:cracked_nether_bricks");
    }

    @Override
    public ItemCrackedNetherBricks toItem() {
        return new ItemCrackedNetherBricks();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRACKED_NETHER_BRICKS;
    }

}