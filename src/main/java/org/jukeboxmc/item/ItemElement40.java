package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement40;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement40 extends Item {

    public ItemElement40() {
        super( "minecraft:element_40", -51 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement40();
    }
}
