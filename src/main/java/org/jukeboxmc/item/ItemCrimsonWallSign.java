package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCrimsonWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonWallSign extends Item {

    public ItemCrimsonWallSign() {
        super ( "minecraft:crimson_wall_sign" );
    }

    @Override
    public BlockCrimsonWallSign getBlock() {
        return new BlockCrimsonWallSign();
    }

    @Override
    public int getMaxAmount() {
        return 16;
    }
}
