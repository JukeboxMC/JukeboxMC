package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement114;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement114 extends Block {

    public BlockElement114() {
        super( "minecraft:element_114" );
    }

    @Override
    public ItemElement114 toItem() {
        return new ItemElement114();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_114;
    }

}
