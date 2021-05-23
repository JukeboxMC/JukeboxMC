package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockQuartzStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemQuartzStairs extends Item {

    public ItemQuartzStairs() {
        super( 156 );
    }

    @Override
    public BlockQuartzStairs getBlock() {
        return new BlockQuartzStairs();
    }
}
