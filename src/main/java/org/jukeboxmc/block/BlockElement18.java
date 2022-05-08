package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement18;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement18 extends Block {

    public BlockElement18() {
        super( "minecraft:element_18" );
    }

    @Override
    public ItemElement18 toItem() {
        return new ItemElement18();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_18;
    }

}
