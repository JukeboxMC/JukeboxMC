package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement101;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement101 extends Item {

    public ItemElement101() {
        super( -112 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement101();
    }
}
