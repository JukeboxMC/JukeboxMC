package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement46;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement46 extends Block {

    public BlockElement46() {
        super( "minecraft:element_46" );
    }

    @Override
    public ItemElement46 toItem() {
        return new ItemElement46();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_46;
    }

}
