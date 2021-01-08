package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement116;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement116 extends Item {

    public ItemElement116() {
        super( "minecraft:element_116", -127 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement116();
    }
}
