package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement77;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement77 extends Block {

    public BlockElement77() {
        super( "minecraft:element_77" );
    }

    @Override
    public ItemElement77 toItem() {
        return new ItemElement77();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_77;
    }

}
