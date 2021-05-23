package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement50;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement50 extends Block {

    public BlockElement50() {
        super( "minecraft:element_50" );
    }

    @Override
    public ItemElement50 toItem() {
        return new ItemElement50();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_50;
    }

}
