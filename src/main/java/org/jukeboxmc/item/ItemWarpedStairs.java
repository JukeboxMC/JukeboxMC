package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedStairs extends Item {

    public ItemWarpedStairs() {
        super( "minecraft:warped_stairs", -255 );
    }

    @Override
    public BlockWarpedStairs getBlock() {
        return new BlockWarpedStairs();
    }
}
