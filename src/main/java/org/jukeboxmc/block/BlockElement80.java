package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement80;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement80 extends Block {

    public BlockElement80() {
        super( "minecraft:element_80" );
    }

    @Override
    public ItemElement80 toItem() {
        return new ItemElement80();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_80;
    }

}
