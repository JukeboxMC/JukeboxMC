package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBlueGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlueGlazedTerracotta extends Item {

    public ItemBlueGlazedTerracotta() {
        super( "minecraft:blue_glazed_terracotta", 231 );
    }

    @Override
    public Block getBlock() {
        return new BlockBlueGlazedTerracotta();
    }
}
