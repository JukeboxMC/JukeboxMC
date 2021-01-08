package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGreenGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGreenGlazedTerracotta extends Item {

    public ItemGreenGlazedTerracotta() {
        super( "minecraft:green_glazed_terracotta", 233 );
    }

    @Override
    public BlockGreenGlazedTerracotta getBlock() {
        return new BlockGreenGlazedTerracotta();
    }
}
