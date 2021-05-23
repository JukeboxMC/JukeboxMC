package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement93;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement93 extends Item {

    public ItemElement93() {
        super( -104 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement93();
    }
}
