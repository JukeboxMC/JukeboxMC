package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement30;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement30 extends Block {

    public BlockElement30() {
        super( "minecraft:element_30" );
    }

    @Override
    public ItemElement30 toItem() {
        return new ItemElement30();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_30;
    }

}
