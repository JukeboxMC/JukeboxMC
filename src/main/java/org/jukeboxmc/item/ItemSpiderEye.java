package org.jukeboxmc.item;

import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSpiderEye extends ItemFoodBehavior {

    public ItemSpiderEye() {
        super( "minecraft:spider_eye" );
    }

    @Override
    public float getSaturation() {
        return 3.2f;
    }

    @Override
    public int getHunger() {
        return 2;
    }
}
