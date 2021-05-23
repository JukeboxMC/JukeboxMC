package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockYellowGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemYellowGlazedTerracotta extends Item {

    public ItemYellowGlazedTerracotta() {
        super( 224 );
    }

    @Override
    public BlockYellowGlazedTerracotta getBlock() {
        return new BlockYellowGlazedTerracotta();
    }
}
