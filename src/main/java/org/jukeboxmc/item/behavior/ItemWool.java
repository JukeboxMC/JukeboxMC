package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockWool;
import org.jukeboxmc.block.data.BlockColor;
import org.jukeboxmc.item.Burnable;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWool extends Item implements Burnable {

    private final BlockWool block;

    public ItemWool( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.WOOL );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemWool( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.WOOL );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemWool setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemWool setColor( BlockColor blockColor ) {
        this.blockRuntimeId = this.block.setColor( blockColor ).getRuntimeId();
        return this;
    }

    public BlockColor getColor() {
        return this.block.getColor();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 100 );
    }
}
