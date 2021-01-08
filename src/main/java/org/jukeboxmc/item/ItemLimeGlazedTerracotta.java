package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLimeGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLimeGlazedTerracotta extends Item {

    public ItemLimeGlazedTerracotta() {
        super( "minecraft:lime_glazed_terracotta", 225 );
    }

    @Override
    public BlockLimeGlazedTerracotta getBlock() {
        return new BlockLimeGlazedTerracotta();
    }
}
