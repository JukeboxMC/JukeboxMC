package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockCoralBlock;
import org.jukeboxmc.block.data.CoralColor;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralBlock extends Item {

    private final @NotNull BlockCoralBlock block;

    public ItemCoralBlock( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.CORAL_BLOCK );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemCoralBlock( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.CORAL_BLOCK );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemCoralBlock setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemCoralBlock setCoralColor(@NotNull CoralColor coralColor ) {
        this.blockRuntimeId = this.block.setCoralColor( coralColor ).getRuntimeId();
        return this;
    }

    public CoralColor getCoralColor() {
        return this.block.getCoralColor();
    }

    public @NotNull ItemCoralBlock setDead(boolean value ) {
        this.blockRuntimeId = this.block.setDead( value ).getRuntimeId();
        return this;
    }

    public boolean isDead() {
        return this.block.isDead();
    }

}
