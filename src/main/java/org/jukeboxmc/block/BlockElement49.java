package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement49;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement49 extends Block {

    public BlockElement49() {
        super( "minecraft:element_49" );
    }

    @Override
    public ItemElement49 toItem() {
        return new ItemElement49();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_49;
    }

}
