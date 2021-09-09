package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPolishedGraniteStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPolishedGraniteStairs extends BlockStairs {

    public BlockPolishedGraniteStairs() {
        super( "minecraft:polished_granite_stairs" );
    }

    @Override
    public ItemPolishedGraniteStairs toItem() {
        return new ItemPolishedGraniteStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POLISHED_GRANITE_STAIRS;
    }

}
