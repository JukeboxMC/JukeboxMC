package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedSign extends Item {

    public ItemWarpedSign() {
        super( 603 );
    }

    @Override
    public BlockWarpedStandingSign getBlock() {
        return new BlockWarpedStandingSign();
    }
}
