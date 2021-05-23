package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBlackGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlackGlazedTerracotta extends Item {

    public ItemBlackGlazedTerracotta() {
        super( 235 );
    }

    @Override
    public BlockBlackGlazedTerracotta getBlock() {
        return new BlockBlackGlazedTerracotta();
    }
}
