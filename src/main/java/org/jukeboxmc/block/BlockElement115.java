package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement115;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement115 extends Block {

    public BlockElement115() {
        super( "minecraft:element_115" );
    }

    @Override
    public ItemElement115 toItem() {
        return new ItemElement115();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_115;
    }

}
