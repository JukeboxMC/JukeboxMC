package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaWallSign extends Item {

    public ItemAcaciaWallSign() {
        super( -191 );
    }

    @Override
    public BlockAcaciaWallSign getBlock() {
        return new BlockAcaciaWallSign();
    }
}
