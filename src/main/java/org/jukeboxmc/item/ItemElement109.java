package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement109;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement109 extends Item {

    public ItemElement109() {
        super( -120 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement109();
    }
}
