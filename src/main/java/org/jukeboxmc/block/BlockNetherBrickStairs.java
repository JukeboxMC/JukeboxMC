package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemNetherBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNetherBrickStairs extends BlockStairs {

    public BlockNetherBrickStairs() {
        super( "minecraft:nether_brick_stairs" );
    }

    @Override
    public ItemNetherBrickStairs toItem() {
        return new ItemNetherBrickStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.NETHER_BRICK_STAIRS;
    }

}
