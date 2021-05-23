package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement107;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement107 extends Block {

    public BlockElement107() {
        super( "minecraft:element_107" );
    }

    @Override
    public ItemElement107 toItem() {
        return new ItemElement107();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_107;
    }

}
