package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockMagentaGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMagentaGlazedTerracotta extends Item {

    public ItemMagentaGlazedTerracotta() {
        super ( "minecraft:magenta_glazed_terracotta" );
    }

    @Override
    public BlockMagentaGlazedTerracotta getBlock() {
        return new BlockMagentaGlazedTerracotta();
    }
}
