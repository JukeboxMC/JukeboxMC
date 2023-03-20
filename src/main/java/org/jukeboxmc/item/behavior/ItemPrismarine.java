package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockPrismarine;
import org.jukeboxmc.block.data.PrismarineType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPrismarine extends Item {

    private final @NotNull BlockPrismarine block;

    public ItemPrismarine( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.PRISMARINE );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemPrismarine( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.PRISMARINE );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemPrismarine setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemPrismarine setPrismarineType(@NotNull PrismarineType prismarineType ) {
        this.blockRuntimeId = this.block.setPrismarineType( prismarineType ).getRuntimeId();
        return this;
    }

    public PrismarineType getWoodType() {
        return this.block.getPrismarineType();
    }
}
