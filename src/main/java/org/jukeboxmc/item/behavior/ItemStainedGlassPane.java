package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockStainedGlassPane;
import org.jukeboxmc.block.data.BlockColor;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStainedGlassPane extends Item {

    private final @NotNull BlockStainedGlassPane block;

    public ItemStainedGlassPane( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.STAINED_GLASS_PANE );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemStainedGlassPane( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.STAINED_GLASS_PANE );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemStainedGlassPane setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemStainedGlassPane setColor(@NotNull BlockColor blockColor ) {
        this.blockRuntimeId = this.block.setColor( blockColor ).getRuntimeId();
        return this;
    }

    public BlockColor getColor() {
        return this.block.getColor();
    }
}
