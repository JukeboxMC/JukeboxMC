package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement58;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement58 extends Block {

    public BlockElement58() {
        super( "minecraft:element_58" );
    }

    @Override
    public ItemElement58 toItem() {
        return new ItemElement58();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_58;
    }

}
