package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockSand;
import org.jukeboxmc.block.data.SandType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSand extends Item {

    private final @NotNull BlockSand block;

    public ItemSand( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.SAND );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemSand( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.SAND );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemSand setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemSand setSandType(@NotNull SandType sandType ) {
        this.blockRuntimeId = this.block.setSandType( sandType ).getRuntimeId();
        return this;
    }

    public SandType getSandType() {
        return this.block.getSandType();
    }
}
