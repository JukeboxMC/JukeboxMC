package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement36;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement60 extends Item {

    public ItemElement60() {
        super( "minecraft:element_60", -71 );
    }

    @Override
    public Block getBlock() {
        return new BlockElement36();
    }
}
