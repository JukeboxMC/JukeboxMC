package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement71;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement71 extends Item {

    public ItemElement71() {
        super ( "minecraft:element_71" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement71();
    }
}
