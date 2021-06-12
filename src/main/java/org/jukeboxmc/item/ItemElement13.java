package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement13;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement13 extends Item {

    public ItemElement13() {
        super ( "minecraft:element_13" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement13();
    }
}
