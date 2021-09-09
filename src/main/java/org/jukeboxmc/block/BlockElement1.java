package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement1;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement1 extends Block {

    public BlockElement1() {
        super( "minecraft:element_1" );
    }

    @Override
    public ItemElement1 toItem() {
        return new ItemElement1();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_1;
    }

}
