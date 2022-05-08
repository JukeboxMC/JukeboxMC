package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement24;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement24 extends Block {

    public BlockElement24() {
        super( "minecraft:element_24" );
    }

    @Override
    public ItemElement24 toItem() {
        return new ItemElement24();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_24;
    }

}
