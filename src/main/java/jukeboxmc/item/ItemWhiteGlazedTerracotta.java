package jukeboxmc.item;

import org.jukeboxmc.block.BlockWhiteGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWhiteGlazedTerracotta extends Item {

    public ItemWhiteGlazedTerracotta() {
        super ( "minecraft:white_glazed_terracotta" );
    }

    @Override
    public BlockWhiteGlazedTerracotta getBlock() {
        return new BlockWhiteGlazedTerracotta();
    }
}
