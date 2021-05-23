package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement55;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement55 extends Item {

    public ItemElement55() {
        super( -66 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement55();
    }
}
