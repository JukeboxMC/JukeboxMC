package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGraniteStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGraniteStairs extends BlockStairs {

    public BlockGraniteStairs() {
        super( "minecraft:granite_stairs" );
    }

    @Override
    public ItemGraniteStairs toItem() {
        return new ItemGraniteStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GRANITE_STAIRS;
    }

}
