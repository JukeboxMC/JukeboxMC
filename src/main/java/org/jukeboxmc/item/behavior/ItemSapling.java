package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockSapling;
import org.jukeboxmc.block.data.SaplingType;
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
public class ItemSapling extends Item implements Burnable {

    private final BlockSapling block;

    public ItemSapling( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.SAPLING);
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemSapling( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.SAPLING);
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemSapling setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemSapling setSaplingType( SaplingType saplingType ) {
        this.blockRuntimeId = this.block.setSaplingType( saplingType ).getRuntimeId();
        return this;
    }

    public SaplingType getLogType() {
        return this.block.getSaplingType();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 100 );
    }
}
