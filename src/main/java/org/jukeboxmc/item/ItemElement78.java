package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement78;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement78 extends Item {

    public ItemElement78() {
        super( -89 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement78();
    }
}
