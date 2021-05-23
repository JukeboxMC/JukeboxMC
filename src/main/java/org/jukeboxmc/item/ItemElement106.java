package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement106;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement106 extends Item {

    public ItemElement106() {
        super( -117 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement106();
    }
}
