package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPinkGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPinkGlazedTerracotta extends Item {

    public ItemPinkGlazedTerracotta() {
        super( 226 );
    }

    @Override
    public BlockPinkGlazedTerracotta getBlock() {
        return new BlockPinkGlazedTerracotta();
    }
}
