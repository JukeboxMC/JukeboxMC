package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBlackstoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlackstoneStairs extends Item {

    public ItemBlackstoneStairs() {
        super( -276 );
    }

    @Override
    public BlockBlackstoneStairs getBlock() {
        return new BlockBlackstoneStairs();
    }
}
