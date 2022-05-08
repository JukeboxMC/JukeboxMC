package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCobblestoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCobblestoneStairs extends BlockStairs {

    public BlockCobblestoneStairs() {
        super( "minecraft:stone_stairs" );
    }

    @Override
    public ItemCobblestoneStairs toItem() {
        return new ItemCobblestoneStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.STONE_STAIRS;
    }

}
