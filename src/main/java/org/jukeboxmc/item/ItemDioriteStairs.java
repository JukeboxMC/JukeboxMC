package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDioriteStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDioriteStairs extends Item {

    public ItemDioriteStairs() {
        super( -170 );
    }

    @Override
    public BlockDioriteStairs getBlock() {
        return new BlockDioriteStairs();
    }
}
