package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockStoneSlab2;
import org.jukeboxmc.block.data.StoneSlab2Type;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneSlab2 extends Item {

    private final @NotNull BlockStoneSlab2 block;

    public ItemStoneSlab2( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.STONE_BLOCK_SLAB2 );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemStoneSlab2( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.STONE_BLOCK_SLAB2 );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemStoneSlab2 setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemStoneSlab2 setStoneType(@NotNull StoneSlab2Type stoneSlabType ) {
        this.blockRuntimeId = this.block.setStoneSlabType( stoneSlabType ).getRuntimeId();
        return this;
    }

    public StoneSlab2Type getStoneType() {
        return this.block.getStoneSlabType();
    }
}
