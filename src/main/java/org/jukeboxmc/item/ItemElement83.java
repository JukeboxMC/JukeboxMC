package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement83;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement83 extends Item {

    public ItemElement83() {
        super( -94 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement83();
    }
}
