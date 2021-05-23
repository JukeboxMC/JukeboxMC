package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement85;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement85 extends Block {

    public BlockElement85() {
        super( "minecraft:element_85" );
    }

    @Override
    public ItemElement85 toItem() {
        return new ItemElement85();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_85;
    }

}
