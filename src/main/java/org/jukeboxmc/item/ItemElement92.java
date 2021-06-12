package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement92;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement92 extends Item {

    public ItemElement92() {
        super ( "minecraft:element_92" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement92();
    }
}
