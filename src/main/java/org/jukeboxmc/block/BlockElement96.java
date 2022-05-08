package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement96;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement96 extends Block {

    public BlockElement96() {
        super( "minecraft:element_96" );
    }

    @Override
    public ItemElement96 toItem() {
        return new ItemElement96();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_96;
    }

}
