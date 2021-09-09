package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement7;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement7 extends Block {

    public BlockElement7() {
        super( "minecraft:element_7" );
    }

    @Override
    public ItemElement7 toItem() {
        return new ItemElement7();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_7;
    }

}
