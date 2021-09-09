package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement100;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement100 extends Block {

    public BlockElement100() {
        super( "minecraft:element_100" );
    }

    @Override
    public ItemElement100 toItem() {
        return new ItemElement100();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_100;
    }

}
