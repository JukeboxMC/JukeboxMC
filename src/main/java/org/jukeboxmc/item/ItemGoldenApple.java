package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGoldenApple extends ItemFoodBehavior {

    public ItemGoldenApple() {
        super ( "minecraft:golden_apple" );
    }

    @Override
    public float getSaturation() {
        return 9.6f;
    }

    @Override
    public int getHunger() {
        return 4;
    }
}
