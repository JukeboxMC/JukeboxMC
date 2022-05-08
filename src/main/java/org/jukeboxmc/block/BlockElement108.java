package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement108;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement108 extends Block {

    public BlockElement108() {
        super( "minecraft:element_108" );
    }

    @Override
    public ItemElement108 toItem() {
        return new ItemElement108();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_108;
    }

}
