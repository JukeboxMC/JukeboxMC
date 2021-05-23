package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement36;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement36 extends Item {

    public ItemElement36() {
        super( -47 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement36();
    }
}
