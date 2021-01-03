package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBlackGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlackGlazedTerracotta extends Item {

    public ItemBlackGlazedTerracotta() {
        super( "minecraft:black_glazed_terracotta", 235 );
    }

    @Override
    public Block getBlock() {
        return new BlockBlackGlazedTerracotta();
    }
}
