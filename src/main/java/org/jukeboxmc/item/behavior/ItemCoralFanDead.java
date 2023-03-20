package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockCoralFanDead;
import org.jukeboxmc.block.data.CoralColor;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralFanDead extends Item {

    private final @NotNull BlockCoralFanDead block;

    public ItemCoralFanDead( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.CORAL_FAN_DEAD );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemCoralFanDead( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.CORAL_FAN_DEAD );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemCoralFanDead setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemCoralFanDead setCoralColor(@NotNull CoralColor coralColor ) {
        this.blockRuntimeId = this.block.setCoralColor( coralColor ).getRuntimeId();
        return this;
    }

    public CoralColor getCoralColor() {
        return this.block.getCoralColor();
    }
}
