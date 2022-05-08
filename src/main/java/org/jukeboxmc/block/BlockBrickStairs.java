package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBrickStairs extends BlockStairs {

    public BlockBrickStairs() {
        super( "minecraft:brick_stairs" );
    }

    @Override
    public ItemBrickStairs toItem() {
        return new ItemBrickStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.BRICK_STAIRS;
    }

}
