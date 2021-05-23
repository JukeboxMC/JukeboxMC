package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDarkOakStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakStairs extends Item {

    public ItemDarkOakStairs() {
        super( 164 );
    }

    @Override
    public BlockDarkOakStairs getBlock() {
        return new BlockDarkOakStairs();
    }
}
