package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDioriteStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDioriteStairs extends BlockStairs {

    public BlockDioriteStairs() {
        super( "minecraft:diorite_stairs" );
    }

    @Override
    public ItemDioriteStairs toItem() {
        return new ItemDioriteStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DIORITE_STAIRS;
    }

}
