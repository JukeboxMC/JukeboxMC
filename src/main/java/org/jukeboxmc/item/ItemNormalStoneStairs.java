package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockNormalStoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNormalStoneStairs extends Item {

    public ItemNormalStoneStairs() {
        super( -180 );
    }

    @Override
    public BlockNormalStoneStairs getBlock() {
        return new BlockNormalStoneStairs();
    }
}
