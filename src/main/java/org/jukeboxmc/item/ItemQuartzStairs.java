package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockQuartzStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemQuartzStairs extends Item {

    public ItemQuartzStairs() {
        super ( "minecraft:quartz_stairs" );
    }

    @Override
    public BlockQuartzStairs getBlock() {
        return new BlockQuartzStairs();
    }
}
