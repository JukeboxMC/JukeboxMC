package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWater;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaterBucket extends AbstractItemBucket {

    public ItemWaterBucket() {
        super ( "minecraft:water_bucket" );
    }

    @Override
    public BlockWater getBlock() {
        return new BlockWater();
    }
}
