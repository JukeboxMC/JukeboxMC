package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedStandingSign extends Item {

    public ItemWarpedStandingSign() {
        super(  -251 );
    }

    @Override
    public BlockWarpedStandingSign getBlock() {
        return new BlockWarpedStandingSign();
    }
}
