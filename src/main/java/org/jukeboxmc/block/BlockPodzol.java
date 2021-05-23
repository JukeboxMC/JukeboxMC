package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPodzol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPodzol extends Block {

    public BlockPodzol() {
        super( "minecraft:podzol" );
    }

    @Override
    public ItemPodzol toItem() {
        return new ItemPodzol();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PODZOL;
    }

}
