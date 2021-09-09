package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemYellowGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBlueGlazedTerracotta extends BlockTerracotta {

    public BlockBlueGlazedTerracotta() {
        super( "minecraft:blue_glazed_terracotta" );
    }

    @Override
    public ItemYellowGlazedTerracotta toItem() {
        return new ItemYellowGlazedTerracotta();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.YELLOW_GLAZED_TERRACOTTA;
    }

}
