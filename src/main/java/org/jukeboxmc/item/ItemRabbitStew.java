package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRabbitStew extends ItemFoodBehavior {

    public ItemRabbitStew() {
        super ( "minecraft:rabbit_stew" );
    }

    @Override
    public float getSaturation() {
        return 12;
    }

    @Override
    public int getHunger() {
        return 10;
    }
}
