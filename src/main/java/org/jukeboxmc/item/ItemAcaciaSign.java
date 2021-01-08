package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaSign extends Item {

    public ItemAcaciaSign() {
        super( "minecraft:acacia_sign", 569 );
    }

    @Override //TODO Calculate
    public BlockAcaciaWallSign getBlock() {
        return new BlockAcaciaWallSign();
    }
}
