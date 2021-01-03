package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement91;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement91 extends Item {

    public ItemElement91() {
        super( "minecraft:element_91", -102 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement91();
    }
}
