package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement83;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement83 extends Block {

    public BlockElement83() {
        super( "minecraft:element_83" );
    }

    @Override
    public ItemElement83 toItem() {
        return new ItemElement83();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_83;
    }

}
