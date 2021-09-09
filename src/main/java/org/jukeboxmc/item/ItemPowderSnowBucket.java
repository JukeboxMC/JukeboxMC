package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockPowderSnow;
import org.jukeboxmc.item.behavior.ItemBucketBehavior;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPowderSnowBucket extends ItemBucketBehavior {

    public ItemPowderSnowBucket() {
        super( "minecraft:powder_snow_bucket" );
    }

    @Override
    public Block getBlock() {
        return new BlockPowderSnow();
    }

}
