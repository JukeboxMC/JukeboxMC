package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement48;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement48 extends Block {

    public BlockElement48() {
        super( "minecraft:element_48" );
    }

    @Override
    public ItemElement48 toItem() {
        return new ItemElement48();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_48;
    }

}
