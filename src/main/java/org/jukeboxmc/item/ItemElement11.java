package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement11;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement11 extends Item {

    public ItemElement11() {
        super ( "minecraft:element_11" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement11();
    }
}
