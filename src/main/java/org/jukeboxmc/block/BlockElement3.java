package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement3;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement3 extends Block {

    public BlockElement3() {
        super( "minecraft:element_3" );
    }

    @Override
    public ItemElement3 toItem() {
        return new ItemElement3();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_3;
    }

}
