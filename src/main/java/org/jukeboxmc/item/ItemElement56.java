package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement56;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement56 extends Item {

    public ItemElement56() {
        super ( "minecraft:element_56" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement56();
    }
}
