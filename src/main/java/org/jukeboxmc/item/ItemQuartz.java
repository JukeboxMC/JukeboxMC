package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockQuartzBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemQuartz extends Item {

    public ItemQuartz() {
        super( 514 );
    }

    @Override
    public BlockQuartzBlock getBlock() {
        return new BlockQuartzBlock();
    }
}
