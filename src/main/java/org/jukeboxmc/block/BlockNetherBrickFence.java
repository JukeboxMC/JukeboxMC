package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemNetherBrickBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNetherBrickFence extends Block {

    public BlockNetherBrickFence() {
        super( "minecraft:nether_brick_fence" );
    }

    @Override
    public ItemNetherBrickBlock toItem() {
        return new ItemNetherBrickBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NETHER_BRICK_FENCE;
    }

}
