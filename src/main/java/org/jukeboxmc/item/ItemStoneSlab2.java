package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStoneSlab2;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.StoneSlab2Type;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneSlab2 extends Item {

    public ItemStoneSlab2( int blockRuntimeId ) {
        super( "minecraft:stone_block_slab2", blockRuntimeId );
    }

    @Override
    public BlockStoneSlab2 getBlock() {
        return (BlockStoneSlab2) BlockType.getBlock( this.blockRuntimeId );
    }

    public StoneSlab2Type getSlabType() {
        return this.getBlock().getStoneSlabType();
    }

}
