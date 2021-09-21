package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCookedChicken extends ItemFoodBehavior {

    public ItemCookedChicken() {
        super ( "minecraft:cooked_chicken" );
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
