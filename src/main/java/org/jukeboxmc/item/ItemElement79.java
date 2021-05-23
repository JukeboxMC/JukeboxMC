package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement79;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement79 extends Item {

    public ItemElement79() {
        super( -90 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement79();
    }
}
