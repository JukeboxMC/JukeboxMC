package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemAmethystBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAmethystBlock extends Block{

    public BlockAmethystBlock() {
        super( "minecraft:amethyst_block" );
    }

    @Override
    public ItemAmethystBlock toItem() {
        return new ItemAmethystBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.AMETHYST_BLOCK;
    }
}
