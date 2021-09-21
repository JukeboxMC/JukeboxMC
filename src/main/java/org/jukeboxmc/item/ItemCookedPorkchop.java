package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCookedPorkchop extends ItemFoodBehavior {

    public ItemCookedPorkchop() {
        super ( "minecraft:cooked_porkchop" );
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
