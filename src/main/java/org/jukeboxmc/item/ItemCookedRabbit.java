package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCookedRabbit extends ItemFoodBehavior {

    public ItemCookedRabbit() {
        super ( "minecraft:cooked_rabbit" );
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
