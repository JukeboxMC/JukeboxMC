package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSapling;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.SaplingType;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSapling extends Item implements Burnable {

    public ItemSapling( int blockRuntimeId ) {
        super( "minecraft:sapling", blockRuntimeId );
    }

    @Override
    public BlockSapling getBlock() {
        return (BlockSapling) BlockType.getBlock( this.blockRuntimeId );
    }

    public SaplingType getSaplingType() {
        return this.getBlock().getSaplingType();
    }


    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 100 );
    }
}
