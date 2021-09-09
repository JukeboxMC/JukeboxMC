package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement66;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement66 extends Block {

    public BlockElement66() {
        super( "minecraft:element_66" );
    }

    @Override
    public ItemElement66 toItem() {
        return new ItemElement66();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_66;
    }

}
