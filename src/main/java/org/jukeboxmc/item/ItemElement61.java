package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement61;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement61 extends Item {

    public ItemElement61() {
        super( "minecraft:element_61", -72 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement61();
    }
}
