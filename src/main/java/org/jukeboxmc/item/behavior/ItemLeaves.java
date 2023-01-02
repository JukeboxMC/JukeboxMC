package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockLeaves;
import org.jukeboxmc.block.data.LeafType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLeaves extends Item {

    private final BlockLeaves block;

    public ItemLeaves( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.LEAVES );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemLeaves( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.LEAVES );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemLeaves setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemLeaves setLeafType( LeafType leafType ) {
        this.blockRuntimeId = this.block.setLeafType( leafType ).getRuntimeId();
        return this;
    }

    public LeafType getLeafType() {
        return this.block.getLeafType();
    }
}
