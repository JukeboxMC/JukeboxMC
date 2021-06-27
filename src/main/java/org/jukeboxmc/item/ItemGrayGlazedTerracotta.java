package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGrayGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGrayGlazedTerracotta extends Item {

    public ItemGrayGlazedTerracotta() {
        super ( "minecraft:gray_glazed_terracotta" );
    }

    @Override
    public BlockGrayGlazedTerracotta getBlock() {
        return new BlockGrayGlazedTerracotta();
    }
}
