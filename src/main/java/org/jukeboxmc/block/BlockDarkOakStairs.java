package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDarkOakStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDarkOakStairs extends BlockStairs {

    public BlockDarkOakStairs() {
        super( "minecraft:dark_oak_stairs" );
    }

    @Override
    public ItemDarkOakStairs toItem() {
        return new ItemDarkOakStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DARK_OAK_STAIRS;
    }

}
