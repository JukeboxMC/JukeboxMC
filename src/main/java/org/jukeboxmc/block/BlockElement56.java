package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement56;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement56 extends Block {

    public BlockElement56() {
        super( "minecraft:element_56" );
    }

    @Override
    public ItemElement56 toItem() {
        return new ItemElement56();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_56;
    }

}
