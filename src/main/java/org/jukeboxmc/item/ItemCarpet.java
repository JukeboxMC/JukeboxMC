package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCarpet;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.BlockColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCarpet extends Item {

    public ItemCarpet( int blockRuntimeId ) {
        super( 171, blockRuntimeId );
    }

    @Override
    public BlockCarpet getBlock() {
        return (BlockCarpet) BlockType.getBlock( this.blockRuntimeId );
    }


    public BlockColor getColor() {
        return this.getBlock().getColor();
    }

}
