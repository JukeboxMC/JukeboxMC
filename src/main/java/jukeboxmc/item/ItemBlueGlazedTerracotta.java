package jukeboxmc.item;

import org.jukeboxmc.block.BlockBlueGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlueGlazedTerracotta extends Item {

    public ItemBlueGlazedTerracotta() {
        super ( "minecraft:blue_glazed_terracotta" );
    }

    @Override
    public BlockBlueGlazedTerracotta getBlock() {
        return new BlockBlueGlazedTerracotta();
    }
}
