package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAndesiteStairs;
import org.jukeboxmc.item.ItemType;

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
    public BlockType getBlockType() {
        return BlockType.ANDESITE_STAIRS;
    }
}
