package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSand;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.SandType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSand extends Item {

    public ItemSand( int blockRuntimeId ) {
        super( "minecraft:sand", blockRuntimeId );
    }

    @Override
    public BlockSand getBlock() {
        return (BlockSand) BlockType.getBlock( this.blockRuntimeId );
    }

    public SandType getSandType() {
        return this.getBlock().getSandType();
    }

}
