package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement106;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement106 extends Block {

    public BlockElement106() {
        super( "minecraft:element_106" );
    }

    @Override
    public ItemElement106 toItem() {
        return new ItemElement106();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_106;
    }

}
