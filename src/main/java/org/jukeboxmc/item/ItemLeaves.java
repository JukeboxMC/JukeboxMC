package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLeaves;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.LeafType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLeaves extends Item {

    public ItemLeaves( int blockRuntimeId ) {
        super( "minecraft:leaves", blockRuntimeId );
    }

    @Override
    public BlockLeaves getBlock() {
        return (BlockLeaves) BlockType.getBlock( this.blockRuntimeId );
    }

    public LeafType getLeafType() {
        return this.getBlock().getLeafType();
    }

}
