package jukeboxmc.item;

import org.jukeboxmc.block.BlockPinkGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPinkGlazedTerracotta extends Item {

    public ItemPinkGlazedTerracotta() {
        super ( "minecraft:pink_glazed_terracotta" );
    }

    @Override
    public BlockPinkGlazedTerracotta getBlock() {
        return new BlockPinkGlazedTerracotta();
    }
}
