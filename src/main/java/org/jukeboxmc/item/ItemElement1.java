package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement1;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement1 extends Item {

    public ItemElement1() {
        super( -12 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement1();
    }
}
