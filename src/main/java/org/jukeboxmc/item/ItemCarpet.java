package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCarpet;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCarpet extends Item implements Burnable {

    public ItemCarpet( int blockRuntimeId ) {
        super( "minecraft:carpet", blockRuntimeId );
    }

    @Override
    public BlockCarpet getBlock() {
        return (BlockCarpet) BlockType.getBlock( this.blockRuntimeId );
    }


    public BlockColor getColor() {
        return this.getBlock().getColor();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 67 );
    }
}
