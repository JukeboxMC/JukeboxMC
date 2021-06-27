package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateTileDoubleSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateTileDoubleSlab extends Item{

    public ItemDeepslateTileDoubleSlab() {
        super( "minecraft:deepslate_tile_double_slab" );
    }

    @Override
    public BlockDeepslateTileDoubleSlab getBlock() {
        return new BlockDeepslateTileDoubleSlab();
    }
}
