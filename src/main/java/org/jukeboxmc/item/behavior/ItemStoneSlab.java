package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockStoneSlab;
import org.jukeboxmc.block.data.StoneSlabType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneSlab extends Item {

    private final @NotNull BlockStoneSlab block;

    public ItemStoneSlab( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.STONE_BLOCK_SLAB );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemStoneSlab( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.STONE_BLOCK_SLAB );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemStoneSlab setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemStoneSlab setStoneType(@NotNull StoneSlabType stoneSlabType ) {
        this.blockRuntimeId = this.block.setStoneSlabType( stoneSlabType ).getRuntimeId();
        return this;
    }

    public StoneSlabType getStoneType() {
        return this.block.getStoneSlabType();
    }
}
