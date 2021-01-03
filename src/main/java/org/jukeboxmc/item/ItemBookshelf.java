package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBookshelf;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBookshelf extends Item {

    public ItemBookshelf() {
        super( "minecraft:bookshelf", 47 );
    }

    @Override
    public Block getBlock() {
        return new BlockBookshelf();
    }
}
