package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement51;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement51 extends Block {

    public BlockElement51() {
        super( "minecraft:element_51" );
    }

    @Override
    public ItemElement51 toItem() {
        return new ItemElement51();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_51;
    }

}
