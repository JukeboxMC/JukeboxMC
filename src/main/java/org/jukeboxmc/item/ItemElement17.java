package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement17;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement17 extends Item {

    public ItemElement17() {
        super ( "minecraft:element_17" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement17();
    }
}
