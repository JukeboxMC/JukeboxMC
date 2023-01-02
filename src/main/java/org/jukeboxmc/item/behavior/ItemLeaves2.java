package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockLeaves2;
import org.jukeboxmc.block.data.LeafType2;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLeaves2 extends Item {

    private final BlockLeaves2 block;

    public ItemLeaves2( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.LEAVES2 );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemLeaves2( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.LEAVES2 );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemLeaves2 setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemLeaves2 setLeafType( LeafType2 leafType ) {
        this.blockRuntimeId = this.block.setLeafType( leafType ).getRuntimeId();
        return this;
    }

    public LeafType2 getLeafType() {
        return this.block.getLeafType();
    }
}
