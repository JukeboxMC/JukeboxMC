package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGrayGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGrayGlazedTerracotta extends Item {

    public ItemGrayGlazedTerracotta() {
        super( "minecraft:gray_glazed_terracotta", 227 );
    }

    @Override
    public BlockGrayGlazedTerracotta getBlock() {
        return new BlockGrayGlazedTerracotta();
    }
}
