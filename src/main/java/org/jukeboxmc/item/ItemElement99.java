package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement99;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement99 extends Item {

    public ItemElement99() {
        super ( "minecraft:element_99" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement99();
    }
}
