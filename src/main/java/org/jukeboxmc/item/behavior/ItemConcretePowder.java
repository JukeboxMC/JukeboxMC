package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockConcretePowder;
import org.jukeboxmc.block.data.BlockColor;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemConcretePowder extends Item {

    private final @NotNull BlockConcretePowder block;

    public ItemConcretePowder( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.CONCRETE_POWDER);
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemConcretePowder( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.CONCRETE_POWDER);
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemConcretePowder setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemConcretePowder setColor(@NotNull BlockColor blockColor ) {
        this.blockRuntimeId = this.block.setColor( blockColor ).getRuntimeId();
        return this;
    }

    public BlockColor getColor() {
        return this.block.getColor();
    }
}
