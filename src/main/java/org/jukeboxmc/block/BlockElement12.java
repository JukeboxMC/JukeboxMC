package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement12;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement12 extends Block {

    public BlockElement12() {
        super( "minecraft:element_12" );
    }

    @Override
    public ItemElement12 toItem() {
        return new ItemElement12();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_12;
    }

}
