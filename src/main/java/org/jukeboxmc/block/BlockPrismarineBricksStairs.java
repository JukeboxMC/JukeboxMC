package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPrismarineBricksStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPrismarineBricksStairs extends Block {

    public BlockPrismarineBricksStairs() {
        super( "minecraft:prismarine_bricks_stairs" );
    }

    @Override
    public ItemPrismarineBricksStairs toItem() {
        return new ItemPrismarineBricksStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.PRISMARINE_BRICKS_STAIRS;
    }

}
