package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCookedBeef extends ItemFoodBehavior {

    public ItemCookedBeef() {
        super ( "minecraft:cooked_beef" );
    }

    @Override
    public float getSaturation() {
        return 12.8f;
    }

    @Override
    public int getHunger() {
        return 8;
    }
}
