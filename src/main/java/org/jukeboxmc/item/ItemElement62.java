package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement62;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement62 extends Item {

    public ItemElement62() {
        super( -73 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement62();
    }
}
