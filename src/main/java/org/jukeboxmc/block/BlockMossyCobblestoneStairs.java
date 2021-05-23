package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemMossyCobblestoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMossyCobblestoneStairs extends BlockStairs {

    public BlockMossyCobblestoneStairs() {
        super( "minecraft:mossy_cobblestone_stairs" );
    }

    @Override
    public ItemMossyCobblestoneStairs toItem() {
        return new ItemMossyCobblestoneStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MOSSY_COBBLESTONE_STAIRS;
    }

}
