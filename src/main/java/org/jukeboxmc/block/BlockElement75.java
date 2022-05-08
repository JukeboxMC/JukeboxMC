package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement75;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement75 extends Block {

    public BlockElement75() {
        super( "minecraft:element_75" );
    }

    @Override
    public ItemElement75 toItem() {
        return new ItemElement75();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_75;
    }

}
