package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement110;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement110 extends Block {

    public BlockElement110() {
        super( "minecraft:element_110" );
    }

    @Override
    public ItemElement110 toItem() {
        return new ItemElement110();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_110;
    }

}
