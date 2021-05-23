package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement23;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement23 extends Item {

    public ItemElement23() {
        super( -34 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement23();
    }
}
