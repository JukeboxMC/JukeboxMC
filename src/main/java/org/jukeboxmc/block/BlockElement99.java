package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement99;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement99 extends Block {

    public BlockElement99() {
        super( "minecraft:element_99" );
    }

    @Override
    public ItemElement99 toItem() {
        return new ItemElement99();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_99;
    }

}
