package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockQuartzBlock;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.QuartzType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemQuartzBlock extends Item {

    public ItemQuartzBlock( int blockRuntimeId ) {
        super( 155, blockRuntimeId );
    }

    @Override
    public BlockQuartzBlock getBlock() {
        return (BlockQuartzBlock) BlockType.getBlock( this.blockRuntimeId );
    }

    public QuartzType getQuartzType() {
        return this.getBlock().getQuartzType();
    }

}
