package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBlackGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBlackGlazedTerracotta extends BlockTerracotta {

    public BlockBlackGlazedTerracotta() {
        super( "minecraft:black_glazed_terracotta" );
    }

    @Override
    public ItemBlackGlazedTerracotta toItem() {
        return new ItemBlackGlazedTerracotta();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BLACK_GLAZED_TERRACOTTA;
    }

}
