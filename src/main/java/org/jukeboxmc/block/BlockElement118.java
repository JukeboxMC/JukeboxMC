package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement118;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement118 extends Block {

    public BlockElement118() {
        super( "minecraft:element_118" );
    }

    @Override
    public ItemElement118 toItem() {
        return new ItemElement118();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_118;
    }

}
