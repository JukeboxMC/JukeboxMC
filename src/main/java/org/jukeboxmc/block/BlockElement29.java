package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement29;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement29 extends Block {

    public BlockElement29() {
        super( "minecraft:element_29" );
    }

    @Override
    public ItemElement29 toItem() {
        return new ItemElement29();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_29;
    }

}
