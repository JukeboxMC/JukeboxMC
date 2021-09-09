package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement40;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement40 extends Block {

    public BlockElement40() {
        super( "minecraft:element_40" );
    }

    @Override
    public ItemElement40 toItem() {
        return new ItemElement40();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_40;
    }

}
