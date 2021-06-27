package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemCrackedDeepslateTiles;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCrackedDeepslateTiles extends Block{

    public BlockCrackedDeepslateTiles() {
        super( "minecraft:cracked_deepslate_tiles" );
    }

    @Override
    public ItemCrackedDeepslateTiles toItem() {
        return new ItemCrackedDeepslateTiles();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRACKED_DEEPSLATE_TILES;
    }
}
