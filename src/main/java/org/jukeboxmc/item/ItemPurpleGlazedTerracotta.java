package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPurpleGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPurpleGlazedTerracotta extends Item {

    public ItemPurpleGlazedTerracotta() {
        super ( "minecraft:purple_glazed_terracotta" );
    }

    @Override
    public BlockPurpleGlazedTerracotta getBlock() {
        return new BlockPurpleGlazedTerracotta();
    }
}
