package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemOakStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockOakStairs extends BlockStairs {

    public BlockOakStairs() {
        super( "minecraft:oak_stairs" );
    }

    @Override
    public ItemOakStairs toItem() {
        return new ItemOakStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.OAK_STAIRS;
    }

}
