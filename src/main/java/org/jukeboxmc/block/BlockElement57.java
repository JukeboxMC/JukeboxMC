package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement57;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement57 extends Block {

    public BlockElement57() {
        super( "minecraft:element_57" );
    }

    @Override
    public ItemElement57 toItem() {
        return new ItemElement57();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_57;
    }

}
