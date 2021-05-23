package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement67;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement67 extends Item {

    public ItemElement67() {
        super( -78 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement67();
    }
}
