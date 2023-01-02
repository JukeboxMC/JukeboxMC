package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockStoneSlab4;
import org.jukeboxmc.block.data.StoneSlab4Type;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneSlab4 extends Item {

    private final BlockStoneSlab4 block;

    public ItemStoneSlab4( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.STONE_BLOCK_SLAB4 );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemStoneSlab4( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.STONE_BLOCK_SLAB4 );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemStoneSlab4 setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemStoneSlab4 setStoneType( StoneSlab4Type stoneSlabType ) {
        this.blockRuntimeId = this.block.setStoneSlabType( stoneSlabType ).getRuntimeId();
        return this;
    }

    public StoneSlab4Type getStoneType() {
        return this.block.getStoneSlabType();
    }
}
