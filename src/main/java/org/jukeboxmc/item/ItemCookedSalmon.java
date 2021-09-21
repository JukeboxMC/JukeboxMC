package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCookedSalmon extends ItemFoodBehavior {

    public ItemCookedSalmon() {
        super ( "minecraft:cooked_salmon" );
    }

    @Override
    public float getSaturation() {
        return 9.6f;
    }

    @Override
    public int getHunger() {
        return 6;
    }
}
