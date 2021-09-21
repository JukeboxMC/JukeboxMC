package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCarrot extends ItemFoodBehavior {

    public ItemCarrot() {
        super ( "minecraft:carrot" );
    }

    @Override
    public float getSaturation() {
        return 3.6f;
    }

    @Override
    public int getHunger() {
        return 3;
    }
}
