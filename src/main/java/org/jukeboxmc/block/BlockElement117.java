package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement117;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement117 extends Block {

    public BlockElement117() {
        super( "minecraft:element_117" );
    }

    @Override
    public ItemElement117 toItem() {
        return new ItemElement117();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_117;
    }

}
