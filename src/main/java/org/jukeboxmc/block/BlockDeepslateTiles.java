package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDeepslateTiles;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateTiles extends Block{

    public BlockDeepslateTiles() {
        super( "minecraft:deepslate_tiles" );
    }

    @Override
    public ItemDeepslateTiles toItem() {
        return new ItemDeepslateTiles();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_TILES;
    }
}
