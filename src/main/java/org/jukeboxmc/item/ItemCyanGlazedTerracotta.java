package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCyanGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCyanGlazedTerracotta extends Item {

    public ItemCyanGlazedTerracotta() {
        super( "minecraft:cyan_glazed_terracotta", 229 );
    }

    @Override
    public Block getBlock() {
        return new BlockCyanGlazedTerracotta();
    }
}
