package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWhiteGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWhiteGlazedTerracotta extends Item {

    public ItemWhiteGlazedTerracotta() {
        super( 220 );
    }

    @Override
    public BlockWhiteGlazedTerracotta getBlock() {
        return new BlockWhiteGlazedTerracotta();
    }
}
