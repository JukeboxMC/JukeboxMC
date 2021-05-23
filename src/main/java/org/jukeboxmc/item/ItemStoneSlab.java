package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStoneSlab;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.StoneSlabType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneSlab extends Item {

    public ItemStoneSlab( int blockRuntimeId ) {
        super( 44, blockRuntimeId );
    }

    @Override
    public BlockStoneSlab getBlock() {
        return (BlockStoneSlab) BlockType.getBlock( this.blockRuntimeId );
    }

    public StoneSlabType getSlabType() {
        return this.getBlock().getStoneSlabType();
    }

}
