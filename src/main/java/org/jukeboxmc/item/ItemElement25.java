package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement25;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement25 extends Item {

    public ItemElement25() {
        super ( "minecraft:element_25" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement25();
    }
}
