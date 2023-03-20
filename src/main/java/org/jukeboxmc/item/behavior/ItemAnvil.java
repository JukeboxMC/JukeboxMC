package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockAnvil;
import org.jukeboxmc.block.data.AnvilDamage;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAnvil extends Item {

    private final @NotNull BlockAnvil block;

    public ItemAnvil( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.ANVIL );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemAnvil( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.ANVIL );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemAnvil setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemAnvil setDamage(@NotNull AnvilDamage anvilDamage ) {
        this.blockRuntimeId = this.block.setDamage( anvilDamage ).getRuntimeId();
        return this;
    }

    public AnvilDamage getDamage() {
        return this.block.getDamage();
    }
}
