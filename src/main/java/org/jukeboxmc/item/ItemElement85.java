package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement85;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement85 extends Item {

    public ItemElement85() {
        super ( "minecraft:element_85" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement85();
    }
}
