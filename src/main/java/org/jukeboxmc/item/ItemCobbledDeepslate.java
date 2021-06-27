package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCobbledDeepslate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCobbledDeepslate extends Item{

    public ItemCobbledDeepslate() {
        super( "minecraft:cobbled_deepslate" );
    }

    @Override
    public BlockCobbledDeepslate getBlock() {
        return new BlockCobbledDeepslate();
    }
}
