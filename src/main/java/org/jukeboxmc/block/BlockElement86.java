package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement86;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement86 extends Block {

    public BlockElement86() {
        super( "minecraft:element_86" );
    }

    @Override
    public ItemElement86 toItem() {
        return new ItemElement86();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_86;
    }

}
