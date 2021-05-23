package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement37;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement37 extends Block {

    public BlockElement37() {
        super( "minecraft:element_37" );
    }

    @Override
    public ItemElement37 toItem() {
        return new ItemElement37();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_37;
    }

}
