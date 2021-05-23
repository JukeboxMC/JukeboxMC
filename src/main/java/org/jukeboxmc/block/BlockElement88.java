package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement88;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement88 extends Block {

    public BlockElement88() {
        super( "minecraft:element_88" );
    }

    @Override
    public ItemElement88 toItem() {
        return new ItemElement88();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_88;
    }

}
