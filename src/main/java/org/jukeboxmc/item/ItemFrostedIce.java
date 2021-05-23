package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockFrostedIce;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFrostedIce extends Item {

    public ItemFrostedIce() {
        super( 207 );
    }

    @Override
    public BlockFrostedIce getBlock() {
        return new BlockFrostedIce();
    }
}
