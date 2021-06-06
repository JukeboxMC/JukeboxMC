package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWater;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaterBucket extends Item {

    public ItemWaterBucket() {
        super( 362 );
    }

    @Override
    public BlockWater getBlock() {
        return new BlockWater();
    }
}
