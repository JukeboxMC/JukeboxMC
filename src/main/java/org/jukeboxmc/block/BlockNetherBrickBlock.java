package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemNetherBrickBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNetherBrickBlock extends Block {

    public BlockNetherBrickBlock() {
        super( "minecraft:nether_brick" );
    }

    @Override
    public ItemNetherBrickBlock toItem() {
        return new ItemNetherBrickBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NETHER_BRICK_BLOCK;
    }

}
