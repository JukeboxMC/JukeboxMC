package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement90;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement90 extends Block {

    public BlockElement90() {
        super( "minecraft:element_90" );
    }

    @Override
    public ItemElement90 toItem() {
        return new ItemElement90();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_90;
    }

}
