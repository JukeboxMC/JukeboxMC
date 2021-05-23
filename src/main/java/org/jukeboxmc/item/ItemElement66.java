package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement66;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement66 extends Item {

    public ItemElement66() {
        super( -77 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement66();
    }
}
