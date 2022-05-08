package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemAndesiteStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAndesiteStairs extends BlockStairs {

    public BlockAndesiteStairs() {
        super( "minecraft:andesite_stairs" );
    }

    @Override
    public ItemAndesiteStairs toItem() {
        return new ItemAndesiteStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.ANDESITE_STAIRS;
    }
}
