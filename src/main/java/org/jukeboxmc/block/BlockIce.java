package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemIce;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockIce extends Block {

    public BlockIce() {
        super( "minecraft:ice" );
    }

    @Override
    public ItemIce toItem() {
        return new ItemIce();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ICE;
    }

}
