package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCoalBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCoalBlock extends Block {

    public BlockCoalBlock() {
        super( "minecraft:coal_block" );
    }

    @Override
    public ItemCoalBlock toItem() {
        return new ItemCoalBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.COAL_BLOCK;
    }

}
