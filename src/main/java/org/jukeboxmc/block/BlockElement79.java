package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement79;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement79 extends Block {

    public BlockElement79() {
        super( "minecraft:element_79" );
    }

    @Override
    public ItemElement79 toItem() {
        return new ItemElement79();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_79;
    }

}
