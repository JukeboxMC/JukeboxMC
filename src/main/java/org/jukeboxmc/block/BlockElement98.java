package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement98;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement98 extends Block {

    public BlockElement98() {
        super( "minecraft:element_98" );
    }

    @Override
    public ItemElement98 toItem() {
        return new ItemElement98();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_98;
    }

}
