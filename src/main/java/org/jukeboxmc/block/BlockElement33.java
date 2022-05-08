package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement33;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement33 extends Block {

    public BlockElement33() {
        super( "minecraft:element_33" );
    }

    @Override
    public ItemElement33 toItem() {
        return new ItemElement33();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_33;
    }

}
