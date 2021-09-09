package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStoneSlab3;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.StoneSlab3Type;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneSlab3 extends Item {

    public ItemStoneSlab3( int blockRuntimeId ) {
        super( "minecraft:double_stone_slab3", blockRuntimeId );
    }

    @Override
    public BlockStoneSlab3 getBlock() {
        return (BlockStoneSlab3) BlockType.getBlock( this.blockRuntimeId );
    }

    public StoneSlab3Type getSlabType() {
        return this.getBlock().getStoneSlabType();
    }

}
