package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement63;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement63 extends Item {

    public ItemElement63() {
        super ( "minecraft:element_63" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement63();
    }
}
