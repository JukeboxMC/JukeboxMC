package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement49;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement49 extends Item {

    public ItemElement49() {
        super( "minecraft:element_49", -60 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement49();
    }
}
