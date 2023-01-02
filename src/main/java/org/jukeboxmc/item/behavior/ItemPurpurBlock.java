package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockPurpurBlock;
import org.jukeboxmc.block.data.PurpurType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPurpurBlock extends Item {

    private final BlockPurpurBlock block;

    public ItemPurpurBlock( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.PURPUR_BLOCK );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemPurpurBlock( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.PURPUR_BLOCK );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemPurpurBlock setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemPurpurBlock setPurpurType( PurpurType purpurType ) {
        this.blockRuntimeId = this.block.setPurpurType( purpurType ).getRuntimeId();
        return this;
    }

    public PurpurType getPurpurType() {
        return this.block.getPurpurType();
    }
}
