package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement76;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement76 extends Block {

    public BlockElement76() {
        super( "minecraft:element_76" );
    }

    @Override
    public ItemElement76 toItem() {
        return new ItemElement76();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_76;
    }

}
