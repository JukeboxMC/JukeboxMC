package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedDeepslate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedDeepslate extends Item{

    public ItemPolishedDeepslate() {
        super( "minecraft:polished_deepslate" );
    }

    @Override
    public BlockPolishedDeepslate getBlock() {
        return new BlockPolishedDeepslate();
    }
}
