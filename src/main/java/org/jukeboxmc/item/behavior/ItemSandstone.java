package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockSandstone;
import org.jukeboxmc.block.data.SandStoneType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSandstone extends Item {

    private final BlockSandstone block;

    public ItemSandstone( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.SANDSTONE );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemSandstone( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.SANDSTONE );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemSandstone setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemSandstone setSandStoneType( SandStoneType sandStoneType ) {
        this.blockRuntimeId = this.block.setSandStoneType( sandStoneType ).getRuntimeId();
        return this;
    }

    public SandStoneType getSandStoneType() {
        return this.block.getSandStoneType();
    }
}
