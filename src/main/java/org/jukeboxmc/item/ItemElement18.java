package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement18;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement18 extends Item {

    public ItemElement18() {
        super( -29 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement18();
    }
}
