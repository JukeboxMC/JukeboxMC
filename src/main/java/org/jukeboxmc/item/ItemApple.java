package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemApple extends ItemFoodBehavior {

    public ItemApple() {
        super ( "minecraft:apple" );
    }

    @Override
    public float getSaturation() {
        return 2.4f;
    }

    @Override
    public int getHunger() {
        return 4;
    }
}
