package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedSlab extends Item {

    public ItemWarpedSlab() {
        super( -265 );
    }

    @Override
    public BlockWarpedSlab getBlock() {
        return new BlockWarpedSlab();
    }
}
