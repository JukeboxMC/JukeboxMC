package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement16;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement16 extends Item {

    public ItemElement16() {
        super ( "minecraft:element_16" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement16();
    }
}
