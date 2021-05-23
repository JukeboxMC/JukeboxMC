package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBookshelf;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBookshelf extends Block {

    public BlockBookshelf() {
        super( "minecraft:bookshelf" );
    }

    @Override
    public ItemBookshelf toItem() {
        return new ItemBookshelf();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BOOKSHELF;
    }

}
