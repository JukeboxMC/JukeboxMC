package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement117;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement117 extends Item {

    public ItemElement117() {
        super( -128 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement117();
    }
}
