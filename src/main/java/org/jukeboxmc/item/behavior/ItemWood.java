package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockWood;
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
public class ItemWood extends Item implements Burnable {

    private final BlockWood block;

    public ItemWood( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.WOOD );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemWood( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.WOOD );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemWood setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemWood setWoodType( WoodType woodType ) {
        this.blockRuntimeId = this.block.setWoodType( woodType ).getRuntimeId();
        return this;
    }

    public WoodType getWoodType() {
        return this.block.getWoodType();
    }

    public ItemWood setStripped( boolean value ) {
        this.blockRuntimeId = this.block.setStripped( value ).getRuntimeId();
        return this;
    }

    public boolean isStripped() {
        return this.block.isStripped();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
