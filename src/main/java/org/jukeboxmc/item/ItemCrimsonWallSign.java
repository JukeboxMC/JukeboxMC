package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCrimsonWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonWallSign extends Item {

    public ItemCrimsonWallSign() {
        super( -252 );
    }

    @Override
    public BlockCrimsonWallSign getBlock() {
        return new BlockCrimsonWallSign();
    }
}
