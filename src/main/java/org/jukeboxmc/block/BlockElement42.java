package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement42;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement42 extends Block {

    public BlockElement42() {
        super( "minecraft:element_42" );
    }

    @Override
    public ItemElement42 toItem() {
        return new ItemElement42();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_42;
    }

}
