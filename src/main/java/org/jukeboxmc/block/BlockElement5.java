package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement5;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement5 extends Block {

    public BlockElement5() {
        super( "minecraft:element_5" );
    }

    @Override
    public ItemElement5 toItem() {
        return new ItemElement5();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_5;
    }

}
