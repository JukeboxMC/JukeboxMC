package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement87;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement87 extends Item {

    public ItemElement87() {
        super( -98 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement87();
    }
}
