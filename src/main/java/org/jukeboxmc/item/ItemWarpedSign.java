package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedSign extends Item {

    public ItemWarpedSign() {
        super ( "minecraft:warped_sign" );
    }

    @Override
    public BlockWarpedStandingSign getBlock() {
        return new BlockWarpedStandingSign();
    }

    @Override
    public int getMaxAmount() {
        return 16;
    }
}
