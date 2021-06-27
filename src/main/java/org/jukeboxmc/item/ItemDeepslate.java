package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslate extends Item{

    public ItemDeepslate() {
        super( "minecraft:deepslate" );
    }

    @Override
    public BlockDeepslate getBlock() {
        return new BlockDeepslate();
    }
}
