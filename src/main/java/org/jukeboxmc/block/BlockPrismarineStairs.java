package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPrismarineStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPrismarineStairs extends BlockStairs {

    public BlockPrismarineStairs() {
        super( "minecraft:prismarine_stairs" );
    }

    @Override
    public ItemPrismarineStairs toItem() {
        return new ItemPrismarineStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.PRISMARINE_STAIRS;
    }

}
