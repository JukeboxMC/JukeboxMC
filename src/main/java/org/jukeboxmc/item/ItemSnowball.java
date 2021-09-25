package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSnowball extends Item {

    public ItemSnowball() {
        super ( "minecraft:snowball" );
    }

    @Override
    public int getMaxAmount() {
        return 16;
    }
}
