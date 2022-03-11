package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLava;
import org.jukeboxmc.item.behavior.ItemBucketBehavior;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLavaBucket extends ItemBucketBehavior implements Burnable {

    public ItemLavaBucket() {
        super ( "minecraft:lava_bucket" );
    }

    @Override
    public BlockLava getBlock() {
        return new BlockLava();
    }

    @Override
    public int getMaxAmount() {
        return 1;
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 20000 );
    }
}
