package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement73;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement73 extends Item {

    public ItemElement73() {
        super( -84 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement73();
    }
}
