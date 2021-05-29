package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemAcaciaStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAcaciaStairs extends BlockStairs {

    public BlockAcaciaStairs() {
        super( "minecraft:acacia_stairs" );
    }

    @Override
    public ItemAcaciaStairs toItem() {
        return new ItemAcaciaStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ACACIA_STAIRS;
    }

}
