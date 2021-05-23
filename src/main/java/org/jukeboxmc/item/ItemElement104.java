package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement104;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement104 extends Item {

    public ItemElement104() {
        super( -115 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement104();
    }
}
