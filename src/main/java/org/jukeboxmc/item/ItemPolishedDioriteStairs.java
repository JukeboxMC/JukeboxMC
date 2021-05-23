package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedDioriteStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedDioriteStairs extends Item {

    public ItemPolishedDioriteStairs() {
        super( -173 );
    }

    @Override
    public BlockPolishedDioriteStairs getBlock() {
        return new BlockPolishedDioriteStairs();
    }
}
