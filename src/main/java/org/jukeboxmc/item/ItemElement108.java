package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement108;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement108 extends Item {

    public ItemElement108() {
        super( "minecraft:element_108", -119 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement108();
    }
}
