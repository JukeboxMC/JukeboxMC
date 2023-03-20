package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockNetherWart;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherWart extends Item {

    private final @NotNull BlockNetherWart block;

    public ItemNetherWart( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.NETHER_WART );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemNetherWart( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.NETHER_WART );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemNetherWart setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

}
