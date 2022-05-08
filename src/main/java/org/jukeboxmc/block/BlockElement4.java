package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement4;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement4 extends Block {

    public BlockElement4() {
        super( "minecraft:element_4" );
    }

    @Override
    public ItemElement4 toItem() {
        return new ItemElement4();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_4;
    }

}
