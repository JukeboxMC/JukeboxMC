package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockCarpet;
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
public class ItemCarpet extends Item implements Burnable {

    private final @NotNull BlockCarpet block;

    public ItemCarpet( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.CARPET );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemCarpet( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.CARPET );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemCarpet setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemCarpet setColor(@NotNull BlockColor blockColor ) {
        this.blockRuntimeId = this.block.setColor( blockColor ).getRuntimeId();
        return this;
    }

    public BlockColor getColor() {
        return this.block.getColor();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 67 );
    }
}
