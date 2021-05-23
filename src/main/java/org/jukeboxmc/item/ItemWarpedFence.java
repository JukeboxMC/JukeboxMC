package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedFence;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedFence extends Item {

    public ItemWarpedFence() {
        super( -257 );
    }

    @Override
    public BlockWarpedFence getBlock() {
        return new BlockWarpedFence();
    }
}
