package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSponge;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.SpongeType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSponge extends Item {

    public ItemSponge( int blockRuntimeId ) {
        super( 19, blockRuntimeId );
    }

    @Override
    public BlockSponge getBlock() {
        return (BlockSponge) BlockType.getBlock( this.blockRuntimeId );
    }

    public SpongeType getSpongeType() {
        return this.getBlock().getSpongeType();
    }
}
