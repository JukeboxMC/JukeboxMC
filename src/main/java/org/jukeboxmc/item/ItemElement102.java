package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement102;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement102 extends Item {

    public ItemElement102() {
        super( -113 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement102();
    }
}
