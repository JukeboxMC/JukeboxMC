package jukeboxmc.item;

import org.jukeboxmc.block.BlockSilverGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSilverGlazedTerracotta extends Item {

    public ItemSilverGlazedTerracotta() {
        super ( "minecraft:silver_glazed_terracotta" );
    }

    @Override
    public BlockSilverGlazedTerracotta getBlock() {
        return new BlockSilverGlazedTerracotta();
    }
}
