package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemLimeGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLimeGlazedTerracotta extends BlockTerracotta {

    public BlockLimeGlazedTerracotta() {
        super( "minecraft:lime_glazed_terracotta" );
    }

    @Override
    public ItemLimeGlazedTerracotta toItem() {
        return new ItemLimeGlazedTerracotta();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LIME_GLAZED_TERRACOTTA;
    }

}
