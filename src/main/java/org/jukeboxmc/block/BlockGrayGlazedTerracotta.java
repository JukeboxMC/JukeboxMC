package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGrayGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGrayGlazedTerracotta extends BlockTerracotta {

    public BlockGrayGlazedTerracotta() {
        super( "minecraft:gray_glazed_terracotta" );
    }

    @Override
    public ItemGrayGlazedTerracotta toItem() {
        return new ItemGrayGlazedTerracotta();
    }

    @Override
    public BlockType getType() {
        return BlockType.GRAY_GLAZED_TERRACOTTA;
    }

}
