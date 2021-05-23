package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement10;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement10 extends Item {

    public ItemElement10() {
        super( 21 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement10();
    }
}
