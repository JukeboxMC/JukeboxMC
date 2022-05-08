package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement61;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement61 extends Block {

    public BlockElement61() {
        super( "minecraft:element_61" );
    }

    @Override
    public ItemElement61 toItem() {
        return new ItemElement61();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_61;
    }

}
