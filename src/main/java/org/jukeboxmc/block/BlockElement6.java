package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement6;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement6 extends Block {

    public BlockElement6() {
        super( "minecraft:element_6" );
    }

    @Override
    public ItemElement6 toItem() {
        return new ItemElement6();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_6;
    }

}
