package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement104;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement104 extends Block {

    public BlockElement104() {
        super( "minecraft:element_104" );
    }

    @Override
    public ItemElement104 toItem() {
        return new ItemElement104();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_104;
    }

}
