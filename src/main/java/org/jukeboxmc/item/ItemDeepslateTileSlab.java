package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateTileSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateTileSlab extends Item {

    public ItemDeepslateTileSlab() {
        super( "minecraft:deepslate_tile_slab" );
    }

    @Override
    public BlockDeepslateTileSlab getBlock() {
        return new BlockDeepslateTileSlab();
    }
}
