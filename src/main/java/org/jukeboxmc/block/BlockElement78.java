package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement78;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement78 extends Block {

    public BlockElement78() {
        super( "minecraft:element_78" );
    }

    @Override
    public ItemElement78 toItem() {
        return new ItemElement78();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_78;
    }

}
