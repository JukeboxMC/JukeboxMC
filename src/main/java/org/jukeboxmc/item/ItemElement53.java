package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement53;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement53 extends Item {

    public ItemElement53() {
        super(  -64 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement53();
    }
}
