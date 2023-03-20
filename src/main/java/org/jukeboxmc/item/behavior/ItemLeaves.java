package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
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

    private final @NotNull BlockLeaves block;

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
    public @NotNull ItemLeaves setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemLeaves setLeafType(@NotNull LeafType leafType ) {
        this.blockRuntimeId = this.block.setLeafType( leafType ).getRuntimeId();
        return this;
    }

    public LeafType getLeafType() {
        return this.block.getLeafType();
    }
}
