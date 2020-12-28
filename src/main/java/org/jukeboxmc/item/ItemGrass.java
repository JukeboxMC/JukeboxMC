package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGrass extends Item {

    public ItemGrass( ) {
        super( "minecraft:grass", 2 );
    }

    @Override
    public int getMaxAmount() {
        return 64;
    }
}
