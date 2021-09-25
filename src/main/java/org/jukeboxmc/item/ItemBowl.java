package org.jukeboxmc.item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBowl extends Item {

    public ItemBowl() {
        super ( "minecraft:bowl" );
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
