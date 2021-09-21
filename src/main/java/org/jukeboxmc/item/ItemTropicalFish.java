package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTropicalFish extends ItemFoodBehavior {

    public ItemTropicalFish() {
        super ( "minecraft:tropical_fish" );
    }

    @Override
    public float getSaturation() {
        return 0.2f;
    }

    @Override
    public int getHunger() {
        return 1;
    }
}
