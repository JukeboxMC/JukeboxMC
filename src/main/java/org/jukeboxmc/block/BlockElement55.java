package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement55;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement55 extends Block {

    public BlockElement55() {
        super( "minecraft:element_55" );
    }

    @Override
    public ItemElement55 toItem() {
        return new ItemElement55();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_55;
    }

}
