package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement54;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement54 extends Block {

    public BlockElement54() {
        super( "minecraft:element_54" );
    }

    @Override
    public ItemElement54 toItem() {
        return new ItemElement54();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_54;
    }

}