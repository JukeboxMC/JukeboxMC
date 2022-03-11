package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.BlockWoodenSlab;
import org.jukeboxmc.block.type.WoodType;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWoodenSlab extends Item implements Burnable {

    public ItemWoodenSlab( int blockRuntimeId ) {
        super( "minecraft:wooden_slab", blockRuntimeId );
    }

    @Override
    public BlockWoodenSlab getBlock() {
        return (BlockWoodenSlab) BlockType.getBlock( this.blockRuntimeId );
    }

    public WoodType getWoodType() {
        return this.getBlock().getWoodType();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 150 );
    }
}
