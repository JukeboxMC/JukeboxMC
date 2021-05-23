package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement65;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement65 extends Item {

    public ItemElement65() {
        super( -76 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement65();
    }
}
