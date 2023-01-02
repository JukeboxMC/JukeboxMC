package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockLog2;
import org.jukeboxmc.block.data.LogType2;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLog2 extends Item {

    private final BlockLog2 block;

    public ItemLog2( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.LOG2 );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemLog2( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.LOG2 );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemLog2 setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemLog2 setLogType( LogType2 logType2 ) {
        this.blockRuntimeId = this.block.setLogType( logType2 ).getRuntimeId();
        return this;
    }

    public LogType2 getLogType() {
        return this.block.getLogType();
    }
}
