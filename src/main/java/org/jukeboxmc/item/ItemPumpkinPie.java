package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPumpkinPie extends ItemFoodBehavior {

    public ItemPumpkinPie() {
        super ( "minecraft:pumpkin_pie" );
    }

    @Override
    public float getSaturation() {
        return 4.8f;
    }

    @Override
    public int getHunger() {
        return 8;
    }
}
