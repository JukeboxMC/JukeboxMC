package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement44;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement44 extends Item {

    public ItemElement44() {
        super( -55 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement44();
    }
}
