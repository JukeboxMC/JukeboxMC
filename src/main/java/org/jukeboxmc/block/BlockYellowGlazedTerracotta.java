package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemYellowGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockYellowGlazedTerracotta extends BlockTerracotta {

    public BlockYellowGlazedTerracotta() {
        super( "minecraft:yellow_glazed_terracotta" );
    }

    @Override
    public ItemYellowGlazedTerracotta toItem() {
        return new ItemYellowGlazedTerracotta();
    }

    @Override
    public BlockType getType() {
        return BlockType.YELLOW_GLAZED_TERRACOTTA;
    }

}
