package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement43;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement43 extends Block {

    public BlockElement43() {
        super( "minecraft:element_43" );
    }

    @Override
    public ItemElement43 toItem() {
        return new ItemElement43();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_43;
    }

}
