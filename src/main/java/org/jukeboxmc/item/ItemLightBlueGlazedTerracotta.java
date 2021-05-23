package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLightBlueGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLightBlueGlazedTerracotta extends Item {

    public ItemLightBlueGlazedTerracotta() {
        super( 223 );
    }

    @Override
    public BlockLightBlueGlazedTerracotta getBlock() {
        return new BlockLightBlueGlazedTerracotta();
    }
}
