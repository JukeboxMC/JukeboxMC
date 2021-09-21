package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCookedCod extends ItemFoodBehavior {

    public ItemCookedCod() {
        super ( "minecraft:cooked_cod" );
    }

    @Override
    public float getSaturation() {
        return 6;
    }

    @Override
    public int getHunger() {
        return 5;
    }
}
