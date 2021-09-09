package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement69;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement69 extends Block {

    public BlockElement69() {
        super( "minecraft:element_69" );
    }

    @Override
    public ItemElement69 toItem() {
        return new ItemElement69();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_69;
    }

}
