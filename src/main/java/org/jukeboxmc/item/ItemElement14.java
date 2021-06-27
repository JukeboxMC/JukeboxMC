package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement14;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement14 extends Item {

    public ItemElement14() {
        super ( "minecraft:element_14" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement14();
    }
}
