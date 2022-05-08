package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBrownGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBrownGlazedTerracotta extends BlockTerracotta {

    public BlockBrownGlazedTerracotta() {
        super( "minecraft:brown_glazed_terracotta" );
    }

    @Override
    public ItemBrownGlazedTerracotta toItem() {
        return new ItemBrownGlazedTerracotta();
    }

    @Override
    public BlockType getType() {
        return BlockType.BROWN_GLAZED_TERRACOTTA;
    }

}
