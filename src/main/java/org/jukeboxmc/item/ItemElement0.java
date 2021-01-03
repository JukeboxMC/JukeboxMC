package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement0;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement0 extends Item {

    public ItemElement0() {
        super( "minecraft:element_0", 36 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement0();
    }
}
