package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSmoothQuartzStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSmoothQuartzStairs extends Item {

    public ItemSmoothQuartzStairs() {
        super( -185 );
    }

    @Override
    public BlockSmoothQuartzStairs getBlock() {
        return new BlockSmoothQuartzStairs();
    }
}
