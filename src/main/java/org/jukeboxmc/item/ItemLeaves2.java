package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLeaves2;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.LeafType2;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLeaves2 extends Item {

    public ItemLeaves2( int blockRuntimeId ) {
        super( 161, blockRuntimeId );
    }

    @Override
    public BlockLeaves2 getBlock() {
        return (BlockLeaves2) BlockType.getBlock( this.blockRuntimeId );
    }

    public LeafType2 getLeafType() {
        return this.getBlock().getLeafType();
    }

}
