package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemEnchantedGoldenApple extends ItemFoodBehavior {

    public ItemEnchantedGoldenApple() {
        super ( "minecraft:enchanted_golden_apple" );
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
