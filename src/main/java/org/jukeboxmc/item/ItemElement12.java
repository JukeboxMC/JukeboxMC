package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement12;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement12 extends Item {

    public ItemElement12() {
        super( -23 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement12();
    }
}
