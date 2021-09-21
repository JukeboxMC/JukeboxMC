package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGoldenCarrot extends ItemFoodBehavior {

    public ItemGoldenCarrot() {
        super ( "minecraft:golden_carrot" );
    }

    @Override
    public float getSaturation() {
        return 14.4f;
    }

    @Override
    public int getHunger() {
        return 6;
    }
}
