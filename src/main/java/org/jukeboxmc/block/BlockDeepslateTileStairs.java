package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDeepslateTileStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateTileStairs extends BlockStairs {

    public BlockDeepslateTileStairs() {
        super( "minecraft:deepslate_tile_stairs" );
    }

    @Override
    public ItemDeepslateTileStairs toItem() {
        return new ItemDeepslateTileStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_TILE_STAIRS;
    }
}
