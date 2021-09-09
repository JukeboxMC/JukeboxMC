package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemOrangeGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockOrangeGlazedTerracotta extends BlockTerracotta {

    public BlockOrangeGlazedTerracotta() {
        super( "minecraft:orange_glazed_terracotta" );
    }

    @Override
    public ItemOrangeGlazedTerracotta toItem() {
        return new ItemOrangeGlazedTerracotta();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ORANGE_GLAZED_TERRACOTTA;
    }

}
