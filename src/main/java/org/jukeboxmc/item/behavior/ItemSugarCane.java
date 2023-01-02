package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockSugarCane;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSugarCane extends Item {

    private final BlockSugarCane block;

    public ItemSugarCane( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.SUGAR_CANE );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemSugarCane( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.SUGAR_CANE );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemSugarCane setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

}
