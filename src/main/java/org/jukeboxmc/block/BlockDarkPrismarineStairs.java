package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDarkPrismarineStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDarkPrismarineStairs extends BlockStairs {

    public BlockDarkPrismarineStairs() {
        super( "minecraft:dark_prismarine_stairs" );
    }

    @Override
    public ItemDarkPrismarineStairs toItem() {
        return new ItemDarkPrismarineStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.DARK_PRISMARINE_STAIRS;
    }

}
