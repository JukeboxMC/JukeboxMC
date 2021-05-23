package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement59;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement59 extends Item {

    public ItemElement59() {
        super( -70 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement59();
    }
}
