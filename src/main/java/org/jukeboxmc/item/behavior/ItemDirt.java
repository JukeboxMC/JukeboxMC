package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockDirt;
import org.jukeboxmc.block.data.DirtType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDirt extends Item {

    private final BlockDirt block;

    public ItemDirt( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.DIRT );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemDirt( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.DIRT );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemDirt setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemDirt setDirtType( DirtType dirtType ) {
        this.blockRuntimeId = this.block.setDirtType( dirtType ).getRuntimeId();
        return this;
    }

    public DirtType getColor() {
        return this.block.getDirtType();
    }
}
