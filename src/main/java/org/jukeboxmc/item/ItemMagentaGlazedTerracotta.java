package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockMagentaGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMagentaGlazedTerracotta extends Item {

    public ItemMagentaGlazedTerracotta() {
        super( 222 );
    }

    @Override
    public BlockMagentaGlazedTerracotta getBlock() {
        return new BlockMagentaGlazedTerracotta();
    }
}
