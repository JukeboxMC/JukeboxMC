package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockStoneSlab3;
import org.jukeboxmc.block.data.StoneSlab3Type;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneSlab3 extends Item {

    private final BlockStoneSlab3 block;

    public ItemStoneSlab3( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.STONE_BLOCK_SLAB3 );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemStoneSlab3( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.STONE_BLOCK_SLAB3 );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemStoneSlab3 setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemStoneSlab3 setStoneType( StoneSlab3Type stoneSlabType ) {
        this.blockRuntimeId = this.block.setStoneSlabType( stoneSlabType ).getRuntimeId();
        return this;
    }

    public StoneSlab3Type getStoneType() {
        return this.block.getStoneSlabType();
    }
}
