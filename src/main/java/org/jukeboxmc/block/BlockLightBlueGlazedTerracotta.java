package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemLightBlueGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLightBlueGlazedTerracotta extends BlockTerracotta {

    public BlockLightBlueGlazedTerracotta() {
        super( "minecraft:light_blue_glazed_terracotta" );
    }

    @Override
    public ItemLightBlueGlazedTerracotta toItem() {
        return new ItemLightBlueGlazedTerracotta();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LIGHT_BLUE_GLAZED_TERRACOTTA;
    }

}
