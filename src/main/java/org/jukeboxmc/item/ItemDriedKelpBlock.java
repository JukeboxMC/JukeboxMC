package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDriedKelpBlock;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDriedKelpBlock extends Item implements Burnable {

    public ItemDriedKelpBlock() {
        super( "minecraft:dried_kelp_block" );
    }

    @Override
    public BlockDriedKelpBlock getBlock() {
        return new BlockDriedKelpBlock();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 4000 );
    }
}
