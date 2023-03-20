package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockCoralFan;
import org.jukeboxmc.block.data.CoralColor;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralFan extends Item {

    private final @NotNull BlockCoralFan block;

    public ItemCoralFan( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.CORAL_FAN );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemCoralFan( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.CORAL_FAN );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemCoralFan setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemCoralFan setCoralColor(@NotNull CoralColor coralColor ) {
        this.blockRuntimeId = this.block.setCoralColor( coralColor ).getRuntimeId();
        return this;
    }

    public CoralColor getCoralColor() {
        return this.block.getCoralColor();
    }
}
