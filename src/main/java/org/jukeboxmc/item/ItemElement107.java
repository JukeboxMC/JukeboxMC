package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement107;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement107 extends Item {

    public ItemElement107() {
        super( -118 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement107();
    }
}
