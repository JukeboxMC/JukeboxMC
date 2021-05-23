package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGrayGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGrayGlazedTerracotta extends Item {

    public ItemGrayGlazedTerracotta() {
        super( 227 );
    }

    @Override
    public BlockGrayGlazedTerracotta getBlock() {
        return new BlockGrayGlazedTerracotta();
    }
}
