package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBeetroot extends ItemFoodBehavior {

    public ItemBeetroot() {
        super ( "minecraft:beetroot" );
    }

    @Override
    public float getSaturation() {
        return 1.2f;
    }

    @Override
    public int getHunger() {
        return 1;
    }
}
