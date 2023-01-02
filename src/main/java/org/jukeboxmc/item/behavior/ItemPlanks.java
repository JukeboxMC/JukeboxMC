package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockPlanks;
import org.jukeboxmc.block.data.WoodType;
import org.jukeboxmc.item.Burnable;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPlanks extends Item implements Burnable {

    private final BlockPlanks block;

    public ItemPlanks( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.PLANKS );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemPlanks( Identifier identifier) {
        super( identifier );
        this.block = Block.create( BlockType.PLANKS );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemPlanks setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemPlanks setWoodType( WoodType woodType ) {
        this.blockRuntimeId = this.block.setWoodType( woodType ).getRuntimeId();
        return this;
    }

    public WoodType getWoodType() {
        return this.block.getWoodType();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
