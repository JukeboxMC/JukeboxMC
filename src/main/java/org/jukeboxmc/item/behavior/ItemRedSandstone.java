package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockRedSandstone;
import org.jukeboxmc.block.data.SandStoneType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedSandstone extends Item {

    private final @NotNull BlockRedSandstone block;

    public ItemRedSandstone( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.RED_SANDSTONE);
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemRedSandstone( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.RED_SANDSTONE);
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemRedSandstone setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemRedSandstone setPrismarineType(@NotNull SandStoneType sandStoneType ) {
        this.blockRuntimeId = this.block.setSandStoneType( sandStoneType ).getRuntimeId();
        return this;
    }

    public SandStoneType getWoodType() {
        return this.block.getSandStoneType();
    }

}
