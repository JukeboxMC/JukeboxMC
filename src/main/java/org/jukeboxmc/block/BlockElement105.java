package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement105;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement105 extends Block {

    public BlockElement105() {
        super( "minecraft:element_105" );
    }

    @Override
    public ItemElement105 toItem() {
        return new ItemElement105();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_105;
    }

}
