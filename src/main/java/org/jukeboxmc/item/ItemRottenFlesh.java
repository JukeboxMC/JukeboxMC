package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRottenFlesh extends ItemFoodBehavior {

    public ItemRottenFlesh() {
        super ( "minecraft:rotten_flesh" );
    }

    @Override
    public float getSaturation() {
        return 0.8f;
    }

    @Override
    public int getHunger() {
        return 4;
    }
}
