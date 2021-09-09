package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateBrickStairs extends Item{

    public ItemDeepslateBrickStairs() {
        super( "minecraft:deepslate_brick_stairs" );
    }

    @Override
    public BlockDeepslateBrickStairs getBlock() {
        return new BlockDeepslateBrickStairs();
    }
}
