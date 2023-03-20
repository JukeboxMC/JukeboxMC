package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockStainedHardenedClay;
import org.jukeboxmc.block.data.BlockColor;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStainedHardenedClay extends Item {

    private final @NotNull BlockStainedHardenedClay block;

    public ItemStainedHardenedClay( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.STAINED_HARDENED_CLAY );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemStainedHardenedClay( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.STAINED_HARDENED_CLAY );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemStainedHardenedClay setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemStainedHardenedClay setColor(@NotNull BlockColor blockColor ) {
        this.blockRuntimeId = this.block.setColor( blockColor ).getRuntimeId();
        return this;
    }

    public BlockColor getColor() {
        return this.block.getColor();
    }
}
