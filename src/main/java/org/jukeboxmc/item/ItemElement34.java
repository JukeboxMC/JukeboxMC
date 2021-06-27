package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement34;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement34 extends Item {

    public ItemElement34() {
        super ( "minecraft:element_34" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement34();
    }
}
