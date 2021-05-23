package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCobblestone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCobblestone extends Block {

    public BlockCobblestone() {
        super( "minecraft:cobblestone" );
    }

    @Override
    public ItemCobblestone toItem() {
        return new ItemCobblestone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.COBBLESTONE;
    }

}
