package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement42;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement42 extends Item {

    public ItemElement42() {
        super ( "minecraft:element_42" );

    }

    @Override
    public Block getBlock() {
        return new BlockElement42();
    }
}
