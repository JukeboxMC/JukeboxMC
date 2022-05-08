package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement17;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement17 extends Block {

    public BlockElement17() {
        super( "minecraft:element_17" );
    }

    @Override
    public ItemElement17 toItem() {
        return new ItemElement17();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_17;
    }

}
