package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement74;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement74 extends Block {

    public BlockElement74() {
        super( "minecraft:element_74" );
    }

    @Override
    public ItemElement74 toItem() {
        return new ItemElement74();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_74;
    }

}
