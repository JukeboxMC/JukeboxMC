package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement59;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement59 extends Block {

    public BlockElement59() {
        super( "minecraft:element_59" );
    }

    @Override
    public ItemElement59 toItem() {
        return new ItemElement59();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_59;
    }

}
