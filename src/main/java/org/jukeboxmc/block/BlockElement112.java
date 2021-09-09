package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement112;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement112 extends Block {

    public BlockElement112() {
        super( "minecraft:element_112" );
    }

    @Override
    public ItemElement112 toItem() {
        return new ItemElement112();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_112;
    }

}
