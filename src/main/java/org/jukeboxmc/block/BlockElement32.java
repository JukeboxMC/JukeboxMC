package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement32;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement32 extends Block {

    public BlockElement32() {
        super( "minecraft:element_32" );
    }

    @Override
    public ItemElement32 toItem() {
        return new ItemElement32();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_32;
    }

}
