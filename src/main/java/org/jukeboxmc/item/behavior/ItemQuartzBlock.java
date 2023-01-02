package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockQuartzBlock;
import org.jukeboxmc.block.data.QuartzType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemQuartzBlock extends Item {

    private final BlockQuartzBlock block;

    public ItemQuartzBlock( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.QUARTZ_BLOCK );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemQuartzBlock( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.QUARTZ_BLOCK );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemQuartzBlock setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemQuartzBlock setPrismarineType( QuartzType quartzType ) {
        this.blockRuntimeId = this.block.setQuartzType( quartzType ).getRuntimeId();
        return this;
    }

    public QuartzType getWoodType() {
        return this.block.getQuartzType();
    }
}
