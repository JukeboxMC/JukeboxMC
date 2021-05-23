package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement27;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement27 extends Block {

    public BlockElement27() {
        super( "minecraft:element_27" );
    }

    @Override
    public ItemElement27 toItem() {
        return new ItemElement27();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_27;
    }

}
