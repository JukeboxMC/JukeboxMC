package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement8;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement8 extends Block {

    public BlockElement8() {
        super( "minecraft:element_8" );
    }

    @Override
    public ItemElement8 toItem() {
        return new ItemElement8();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_8;
    }

}
