package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCyanGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCyanGlazedTerracotta extends Item {

    public ItemCyanGlazedTerracotta() {
        super( 229 );
    }

    @Override
    public BlockCyanGlazedTerracotta getBlock() {
        return new BlockCyanGlazedTerracotta();
    }
}
