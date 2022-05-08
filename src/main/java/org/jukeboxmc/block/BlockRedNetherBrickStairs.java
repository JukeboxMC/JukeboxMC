package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRedNetherBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedNetherBrickStairs extends BlockStairs {

    public BlockRedNetherBrickStairs() {
        super( "minecraft:red_nether_brick_stairs" );
    }

    @Override
    public ItemRedNetherBrickStairs toItem() {
        return new ItemRedNetherBrickStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.RED_NETHER_BRICK_STAIRS;
    }

}
