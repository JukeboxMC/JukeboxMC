package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemStoneBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStoneBrickStairs extends BlockStairs {

    public BlockStoneBrickStairs() {
        super( "minecraft:stone_brick_stairs" );
    }

    @Override
    public ItemStoneBrickStairs toItem() {
        return new ItemStoneBrickStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STONE_BRICK_STAIRS;
    }

}
