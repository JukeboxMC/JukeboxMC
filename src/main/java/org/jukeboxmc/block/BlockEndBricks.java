package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemEndBricks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEndBricks extends Block {

    public BlockEndBricks() {
        super( "minecraft:end_bricks" );
    }

    @Override
    public ItemEndBricks toItem() {
        return new ItemEndBricks();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.END_BRICKS;
    }

}
