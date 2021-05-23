package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement39;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement39 extends Block {

    public BlockElement39() {
        super( "minecraft:element_39" );
    }

    @Override
    public ItemElement39 toItem() {
        return new ItemElement39();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_39;
    }

}
