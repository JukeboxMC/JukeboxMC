package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSmoothSandstoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSmoothSandstoneStairs extends Item {

    public ItemSmoothSandstoneStairs() {
        super ( "minecraft:smooth_sandstone_stairs" );
    }

    @Override
    public BlockSmoothSandstoneStairs getBlock() {
        return new BlockSmoothSandstoneStairs();
    }
}
