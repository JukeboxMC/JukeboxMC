package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement21;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement21 extends Block {

    public BlockElement21() {
        super( "minecraft:element_21" );
    }

    @Override
    public ItemElement21 toItem() {
        return new ItemElement21();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_21;
    }

}
