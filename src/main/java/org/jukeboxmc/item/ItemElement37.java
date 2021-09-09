package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement37;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement37 extends Item {

    public ItemElement37() {
        super ( "minecraft:element_37" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement37();
    }
}
