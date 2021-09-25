package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWater;
import org.jukeboxmc.item.behavior.ItemBucketBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaterBucket extends ItemBucketBehavior {

    public ItemWaterBucket() {
        super ( "minecraft:water_bucket" );
    }

    @Override
    public BlockWater getBlock() {
        return new BlockWater();
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }
}
