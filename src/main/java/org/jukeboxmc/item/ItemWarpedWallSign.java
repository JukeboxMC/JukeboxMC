package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedWallSign extends Item {

    public ItemWarpedWallSign() {
        super ( "minecraft:warped_wall_sign" );
    }

    @Override
    public BlockWarpedWallSign getBlock() {
        return new BlockWarpedWallSign();
    }
}
