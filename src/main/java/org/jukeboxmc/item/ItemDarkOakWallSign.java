package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDarkOakWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakWallSign extends Item {

    public ItemDarkOakWallSign() {
        super( -193 );
    }

    @Override
    public BlockDarkOakWallSign getBlock() {
        return new BlockDarkOakWallSign();
    }
}
