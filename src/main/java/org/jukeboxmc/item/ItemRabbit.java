package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRabbit extends ItemFoodBehavior {

    public ItemRabbit() {
        super ( "minecraft:rabbit" );
    }

    @Override
    public float getSaturation() {
        return 1.8f;
    }

    @Override
    public int getHunger() {
        return 3;
    }
}
