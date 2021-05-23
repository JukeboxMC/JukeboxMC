package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement25;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement25 extends Block {

    public BlockElement25() {
        super( "minecraft:element_25" );
    }

    @Override
    public ItemElement25 toItem() {
        return null;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_25;
    }

}
