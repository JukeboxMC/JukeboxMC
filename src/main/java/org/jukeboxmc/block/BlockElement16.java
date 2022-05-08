package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement16;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement16 extends Block {

    public BlockElement16() {
        super( "minecraft:element_16" );
    }

    @Override
    public ItemElement16 toItem() {
        return new ItemElement16();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_16;
    }

}
