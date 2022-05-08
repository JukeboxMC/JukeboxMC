package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCyanGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCyanGlazedTerracotta extends BlockTerracotta {

    public BlockCyanGlazedTerracotta() {
        super( "minecraft:cyan_glazed_terracotta" );
    }

    @Override
    public ItemCyanGlazedTerracotta toItem() {
        return new ItemCyanGlazedTerracotta();
    }

    @Override
    public BlockType getType() {
        return BlockType.CYAN_GLAZED_TERRACOTTA;
    }

}
