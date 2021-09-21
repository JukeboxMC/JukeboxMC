package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDriedKelp extends ItemFoodBehavior {

    public ItemDriedKelp() {
        super ( "minecraft:dried_kelp" );
    }

    @Override
    public float getSaturation() {
        return 0.2f;
    }

    @Override
    public int getHunger() {
        return 1;
    }
}
