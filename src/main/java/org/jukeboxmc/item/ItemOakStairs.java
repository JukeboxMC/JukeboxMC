package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockOakStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemOakStairs extends Item {

    public ItemOakStairs() {
        super( 53 );
    }

    @Override
    public BlockOakStairs getBlock() {
        return new BlockOakStairs();
    }
}
