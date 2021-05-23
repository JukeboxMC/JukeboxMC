package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedGlazedTerracotta extends Item {

    public ItemRedGlazedTerracotta() {
        super( 234 );
    }

    @Override
    public BlockRedGlazedTerracotta getBlock() {
        return new BlockRedGlazedTerracotta();
    }
}
