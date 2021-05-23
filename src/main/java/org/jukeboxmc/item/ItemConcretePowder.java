package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockConcretePowder;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.BlockColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemConcretePowder extends Item {

    public ItemConcretePowder( int blockRuntimeId ) {
        super( 237, blockRuntimeId );
    }

    @Override
    public BlockConcretePowder getBlock() {
        return (BlockConcretePowder) BlockType.getBlock( this.blockRuntimeId );
    }

    public BlockColor getColor() {
        return this.getBlock().getColor();
    }

}
