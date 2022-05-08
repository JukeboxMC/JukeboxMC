package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement13;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement13 extends Block {

    public BlockElement13() {
        super( "minecraft:element_13" );
    }

    @Override
    public ItemElement13 toItem() {
        return new ItemElement13();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_13;
    }

}
