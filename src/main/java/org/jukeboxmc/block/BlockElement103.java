package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement103;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement103 extends Block {

    public BlockElement103() {
        super( "minecraft:element_103" );
    }

    @Override
    public ItemElement103 toItem() {
        return new ItemElement103();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_103;
    }

}
