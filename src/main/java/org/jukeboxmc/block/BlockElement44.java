package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement44;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement44 extends Block {

    public BlockElement44() {
        super( "minecraft:element_44" );
    }

    @Override
    public ItemElement44 toItem() {
        return new ItemElement44();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_44;
    }

}
