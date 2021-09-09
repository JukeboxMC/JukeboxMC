package jukeboxmc.item;

import org.jukeboxmc.block.BlockYellowGlazedTerracotta;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemYellowGlazedTerracotta extends Item {

    public ItemYellowGlazedTerracotta() {
        super ( "minecraft:yellow_glazed_terracotta" );
    }

    @Override
    public BlockYellowGlazedTerracotta getBlock() {
        return new BlockYellowGlazedTerracotta();
    }
}
