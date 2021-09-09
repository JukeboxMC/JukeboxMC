package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSilverGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSilverGlazedTerracotta extends BlockTerracotta {

    public BlockSilverGlazedTerracotta() {
        super( "minecraft:silver_glazed_terracotta" );
    }

    @Override
    public ItemSilverGlazedTerracotta toItem() {
        return new ItemSilverGlazedTerracotta();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SILVER_GLAZED_TERRACOTTA;
    }

}
