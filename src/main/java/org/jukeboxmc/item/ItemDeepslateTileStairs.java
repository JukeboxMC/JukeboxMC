package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateTileStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateTileStairs extends Item{

    public ItemDeepslateTileStairs() {
        super( "minecraft:deepslate_tile_stairs" );
    }

    @Override
    public BlockDeepslateTileStairs getBlock() {
        return new BlockDeepslateTileStairs();
    }
}
