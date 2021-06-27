package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement113;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement113 extends Item {

    public ItemElement113() {
        super ( "minecraft:element_113" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement113();
    }
}
