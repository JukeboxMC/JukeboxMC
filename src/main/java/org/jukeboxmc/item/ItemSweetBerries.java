package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSweetBerryBush;
import org.jukeboxmc.item.behavior.ItemFoodBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSweetBerries extends ItemFoodBehavior {

    public ItemSweetBerries() {
        super ( "minecraft:sweet_berries" );
    }

    @Override
    public BlockSweetBerryBush getBlock() {
        return new BlockSweetBerryBush();
    }

    @Override
    public float getSaturation() {
        return 1.2f;
    }

    @Override
    public int getHunger() {
        return 2;
    }
}
