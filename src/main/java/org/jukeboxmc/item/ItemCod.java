package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCod extends ItemFoodBehavior {

    public ItemCod() {
        super ( "minecraft:cod" );
    }

    @Override
    public float getSaturation() {
        return 0.4f;
    }

    @Override
    public int getHunger() {
        return 2;
    }
}
