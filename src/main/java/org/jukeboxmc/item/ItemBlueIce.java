package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBlueIce;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlueIce extends Item {

    public ItemBlueIce() {
        super( -11 );
    }

    @Override
    public BlockBlueIce getBlock() {
        return new BlockBlueIce();
    }
}
