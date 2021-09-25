package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMushroomStew extends ItemFoodBehavior {

    public ItemMushroomStew() {
        super ( "minecraft:mushroom_stew" );
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }

    @Override
    public float getSaturation() {
        return 7.2f;
    }

    @Override
    public int getHunger() {
        return 6;
    }

}
