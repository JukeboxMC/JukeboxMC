package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSmoothStone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSmoothStone extends Block {

    public BlockSmoothStone() {
        super( "minecraft:smooth_stone" );
    }

    @Override
    public ItemSmoothStone toItem() {
        return new ItemSmoothStone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SMOOTH_STONE;
    }

}
