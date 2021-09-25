package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaWallSign extends Item {

    public ItemAcaciaWallSign() {
        super( "minecraft:acacia_wall_sign" );
    }

    @Override
    public BlockAcaciaWallSign getBlock() {
        return new BlockAcaciaWallSign();
    }

    @Override
    public int getMaxAmount() {
        return 16;
    }
}
