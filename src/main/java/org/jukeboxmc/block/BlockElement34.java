package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement34;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement34 extends Block {

    public BlockElement34() {
        super( "minecraft:element_34" );
    }

    @Override
    public ItemElement34 toItem() {
        return new ItemElement34();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_34;
    }

}
