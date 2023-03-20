package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockWoodenFence;
import org.jukeboxmc.block.data.WoodType;
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
public class ItemFence extends Item implements Burnable {

    private final @NotNull BlockWoodenFence block;

    public ItemFence( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.FENCE );
    }

    public ItemFence( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.FENCE );
    }

    @Override
    public @NotNull ItemFence setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemFence setWoodType(@NotNull WoodType woodType ) {
        this.blockRuntimeId = this.block.setWoodType( woodType ).getRuntimeId();
        return this;
    }

    public WoodType getWoodType() {
        return this.block.getWoodType();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
