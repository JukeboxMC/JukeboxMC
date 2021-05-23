package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBirchStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBirchStairs extends BlockStairs {

    public BlockBirchStairs() {
        super( "minecraft:birch_stairs" );
    }

    @Override
    public ItemBirchStairs toItem() {
        return new ItemBirchStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BIRCH_STAIRS;
    }

}
