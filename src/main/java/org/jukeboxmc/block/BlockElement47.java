package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement47;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement47 extends Block {

    public BlockElement47() {
        super( "minecraft:element_47" );
    }

    @Override
    public ItemElement47 toItem() {
        return new ItemElement47();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELEMENT_47;
    }

}
