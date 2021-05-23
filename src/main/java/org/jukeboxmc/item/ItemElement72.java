package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement72;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement72 extends Item {

    public ItemElement72() {
        super( -83 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement72();
    }
}
