package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement21;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement21 extends Item {

    public ItemElement21() {
        super( -32 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement21();
    }
}
