package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement41;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement41 extends Item {

    public ItemElement41() {
        super( -52 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement41();
    }
}
