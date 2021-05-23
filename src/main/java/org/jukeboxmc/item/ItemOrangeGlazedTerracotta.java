package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockOrangeGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemOrangeGlazedTerracotta extends Item {

    public ItemOrangeGlazedTerracotta() {
        super( 221 );
    }

    @Override
    public BlockOrangeGlazedTerracotta getBlock() {
        return new BlockOrangeGlazedTerracotta();
    }
}
