package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.BlockWood;
import org.jukeboxmc.block.type.WoodType;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWood extends Item implements Burnable {

    public ItemWood( int blockRuntimeId ) {
        super( "minecraft:wood", blockRuntimeId );
    }

    @Override
    public BlockWood getBlock() {
        return (BlockWood) BlockType.getBlock( this.blockRuntimeId );
    }

    public WoodType getWoodType() {
        return this.getBlock().getWoodType();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
