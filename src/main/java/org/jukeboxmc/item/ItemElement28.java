package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement28;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement28 extends Item {

    public ItemElement28() {
        super( "minecraft:element_28", -39 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement28();
    }
}
