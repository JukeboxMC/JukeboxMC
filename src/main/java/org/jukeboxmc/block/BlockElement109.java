package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement109;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement109 extends Block {

    public BlockElement109() {
        super( "minecraft:element_109" );
    }

    @Override
    public ItemElement109 toItem() {
        return new ItemElement109();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_109;
    }

}
