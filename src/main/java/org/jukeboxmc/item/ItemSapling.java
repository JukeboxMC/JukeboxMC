package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSapling;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.SaplingType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSapling extends Item {

    public ItemSapling( int blockRuntimeId ) {
        super( 6, blockRuntimeId );
    }

    @Override
    public BlockSapling getBlock() {
        return (BlockSapling) BlockType.getBlock( this.blockRuntimeId );
    }

    public SaplingType getSaplingType() {
        return this.getBlock().getSaplingType();
    }


}
