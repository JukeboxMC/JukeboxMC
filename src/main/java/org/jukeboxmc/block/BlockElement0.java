package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement0;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement0 extends Block {

    public BlockElement0() {
        super( "minecraft:element_0" );
    }

    @Override
    public ItemElement0 toItem() {
        return new ItemElement0();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_0;
    }

}
