package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedWallSign extends Item {

    public ItemWarpedWallSign() {
        super( -253 );
    }

    @Override
    public BlockWarpedWallSign getBlock() {
        return new BlockWarpedWallSign();
    }
}
