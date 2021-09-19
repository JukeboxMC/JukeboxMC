package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedSandstone;
import org.jukeboxmc.block.BlockType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedSandstone extends Item {

    public ItemRedSandstone( int blockRuntimeId ) {
        super( "minecraft:red_sandstone", blockRuntimeId );
    }

    @Override
    public BlockRedSandstone getBlock() {
        return (BlockRedSandstone) BlockType.getBlock( this.blockRuntimeId );
    }

}
