package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement28;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement28 extends Block {

    public BlockElement28() {
        super( "minecraft:element_28" );
    }

    @Override
    public ItemElement28 toItem() {
        return new ItemElement28();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_28;
    }

}
