package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedNetherBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedNetherBrickStairs extends Item {

    public ItemRedNetherBrickStairs() {
        super ( "minecraft:red_nether_brick_stairs" );
    }

    @Override
    public BlockRedNetherBrickStairs getBlock() {
        return new BlockRedNetherBrickStairs();
    }
}
