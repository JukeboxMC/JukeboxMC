package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemElement64;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockElement64 extends Block {

    public BlockElement64() {
        super( "minecraft:element_64" );
    }

    @Override
    public ItemElement64 toItem() {
        return new ItemElement64();
    }

    @Override
    public BlockType getType() {
        return BlockType.ELEMENT_64;
    }

}
