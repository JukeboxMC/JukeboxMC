package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedGlazedTerracotta extends Item {

    public ItemRedGlazedTerracotta() {
        super ( "minecraft:red_glazed_terracotta" );
    }

    @Override
    public BlockRedGlazedTerracotta getBlock() {
        return new BlockRedGlazedTerracotta();
    }
}
