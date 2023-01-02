package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockConcrete;
import org.jukeboxmc.block.data.BlockColor;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemConcrete extends Item {

    private final BlockConcrete block;

    public ItemConcrete( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.CONCRETE );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemConcrete( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.CONCRETE );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemConcrete setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemConcrete setColor( BlockColor blockColor ) {
        this.blockRuntimeId = this.block.setColor( blockColor ).getRuntimeId();
        return this;
    }

    public BlockColor getColor() {
        return this.block.getColor();
    }
}
