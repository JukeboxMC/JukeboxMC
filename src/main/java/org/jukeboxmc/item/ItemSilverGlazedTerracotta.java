package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSilverGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSilverGlazedTerracotta extends Item {

    public ItemSilverGlazedTerracotta() {
        super( 228 );
    }

    @Override
    public BlockSilverGlazedTerracotta getBlock() {
        return new BlockSilverGlazedTerracotta();
    }
}
