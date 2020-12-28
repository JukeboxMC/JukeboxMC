package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemYellowGlazedTerracotta extends Item {

    public ItemYellowGlazedTerracotta() {
        super( "minecraft:yellow_glazed_terracotta", 224 );
    }

    @Override
    public int getMaxAmount() {
        return 64;
    }
}
