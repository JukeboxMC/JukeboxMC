package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement112;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement112 extends Item {

    public ItemElement112() {
        super ( "minecraft:element_112" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement112();
    }
}
