package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockLog;
import org.jukeboxmc.block.data.LogType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLog extends Item {

    private final @NotNull BlockLog block;

    public ItemLog( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.LOG );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemLog( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.LOG );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemLog setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemLog setLogType(@NotNull LogType logType ) {
        this.blockRuntimeId = this.block.setLogType( logType ).getRuntimeId();
        return this;
    }

    public LogType getLogType() {
        return this.block.getLogType();
    }
}
