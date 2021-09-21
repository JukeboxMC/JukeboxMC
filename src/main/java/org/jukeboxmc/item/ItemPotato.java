package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPotato extends ItemFoodBehavior {

    public ItemPotato() {
        super ( "minecraft:potato" );
    }

    @Override
    public float getSaturation() {
        return 0.6f;
    }

    @Override
    public int getHunger() {
        return 1;
    }
}
