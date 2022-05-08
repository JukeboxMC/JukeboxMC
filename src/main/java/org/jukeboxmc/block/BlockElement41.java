package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement41;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement41 extends Block {

    public BlockElement41() {
        super( "minecraft:element_41" );
    }

    @Override
    public ItemElement41 toItem() {
        return new ItemElement41();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_41;
    }

}
