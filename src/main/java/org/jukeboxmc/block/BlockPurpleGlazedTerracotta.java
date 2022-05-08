package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPurpleGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPurpleGlazedTerracotta extends BlockTerracotta {

    public BlockPurpleGlazedTerracotta() {
        super( "minecraft:purple_glazed_terracotta" );
    }

    @Override
    public ItemPurpleGlazedTerracotta toItem() {
        return new ItemPurpleGlazedTerracotta();
    }

    @Override
    public BlockType getType() {
        return BlockType.PURPLE_GLAZED_TERRACOTTA;
    }

}
