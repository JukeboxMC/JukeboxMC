package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCyanGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCyanGlazedTerracotta extends Item {

    public ItemCyanGlazedTerracotta() {
        super ( "minecraft:cyan_glazed_terracotta" );
    }

    @Override
    public BlockCyanGlazedTerracotta getBlock() {
        return new BlockCyanGlazedTerracotta();
    }
}
