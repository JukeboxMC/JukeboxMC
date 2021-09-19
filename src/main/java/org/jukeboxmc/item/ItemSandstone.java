package org.jukeboxmc.item;


import org.jukeboxmc.block.BlockSandstone;
import org.jukeboxmc.block.BlockType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSandstone extends Item {

    public ItemSandstone( int blockRuntimeId ) {
        super( "minecraft:sandstone", blockRuntimeId );
    }

    @Override
    public BlockSandstone getBlock() {
        return (BlockSandstone) BlockType.getBlock( this.blockRuntimeId );
    }

}
