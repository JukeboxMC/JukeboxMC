package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.BlockWool;
import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWool extends Item implements Burnable {

    public ItemWool( int blockRuntimeId ) {
        super( "minecraft:wool", blockRuntimeId );
    }

    @Override
    public BlockWool getBlock() {
        return (BlockWool) BlockType.getBlock( this.blockRuntimeId );
    }

    public BlockColor getColor() {
        return this.getBlock().getColor();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 100 );
    }
}
