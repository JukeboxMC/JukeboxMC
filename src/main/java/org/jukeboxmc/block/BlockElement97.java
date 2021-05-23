package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement97;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement97 extends Block {

    public BlockElement97() {
        super( "minecraft:element_97" );
    }

    @Override
    public ItemElement97 toItem() {
        return new ItemElement97();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_97;
    }

}
