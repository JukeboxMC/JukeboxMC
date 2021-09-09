package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement94;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement94 extends Block {

    public BlockElement94() {
        super( "minecraft:element_94" );
    }

    @Override
    public ItemElement94 toItem() {
        return new ItemElement94();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_94;
    }

}
