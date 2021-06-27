package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement26;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement26 extends Item {

    public ItemElement26() {
        super ( "minecraft:element_26" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement26();
    }
}
