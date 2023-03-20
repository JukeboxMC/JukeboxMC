package org.jukeboxmc.item.behavior;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockDoublePlant;
import org.jukeboxmc.block.data.PlantType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDoublePlant extends Item {

    private final @NotNull BlockDoublePlant block;

    public ItemDoublePlant( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.DOUBLE_PLANT );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemDoublePlant( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.DOUBLE_PLANT );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public @NotNull ItemDoublePlant setBlockRuntimeId(int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public @NotNull ItemDoublePlant setPlantType(@NotNull PlantType plantType ) {
        this.blockRuntimeId = this.block.setPlantType( plantType ).getRuntimeId();
        return this;
    }

    public PlantType getPlantType() {
        return this.block.getPlantType();
    }
}
