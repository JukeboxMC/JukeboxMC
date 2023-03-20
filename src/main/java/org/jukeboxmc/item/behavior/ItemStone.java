package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockStone;
import org.jukeboxmc.block.data.StoneType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStone extends Item {

    private final @NotNull BlockStone block;

    public ItemStone( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.STONE );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemStone( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.STONE );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemStone setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemStone setStoneType(@NotNull StoneType stoneType ) {
        this.blockRuntimeId = this.block.setStoneType( stoneType ).getRuntimeId();
        return this;
    }

    public StoneType getColor() {
        return this.block.getStoneType();
    }
}
