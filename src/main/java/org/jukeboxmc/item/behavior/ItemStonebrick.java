package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockStonebrick;
import org.jukeboxmc.block.data.StoneBrickType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStonebrick extends Item {

    private final @NotNull BlockStonebrick block;

    public ItemStonebrick( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.STONEBRICK );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemStonebrick( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.STONEBRICK );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemStonebrick setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemStonebrick setStoneBrickType(@NotNull StoneBrickType stoneBrickType ) {
        this.blockRuntimeId = this.block.setStoneBrickType( stoneBrickType ).getRuntimeId();
        return this;
    }

    public StoneBrickType getStoneBrickType() {
        return this.block.getStoneBrickType();
    }
}
