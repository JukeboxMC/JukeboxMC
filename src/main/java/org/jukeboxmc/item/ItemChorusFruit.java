package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChorusFruit extends ItemFoodBehavior {

    public ItemChorusFruit() {
        super ( "minecraft:chorus_fruit" );
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
