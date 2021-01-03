package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBrownGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBrownGlazedTerracotta extends Item {

    public ItemBrownGlazedTerracotta() {
        super( "minecraft:brown_glazed_terracotta", 232 );
    }

    @Override
    public Block getBlock() {
        return new BlockBrownGlazedTerracotta();
    }
}
