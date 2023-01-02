package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockTallGrass;
import org.jukeboxmc.block.data.GrassType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTallGrass extends Item {

    private final BlockTallGrass block;

    public ItemTallGrass( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.TALLGRASS );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemTallGrass( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.TALLGRASS );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemTallGrass setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemTallGrass setGrassType( GrassType grassType ) {
        this.blockRuntimeId = this.block.setGrassType( grassType ).getRuntimeId();
        return this;
    }

    public GrassType getGrassType() {
        return this.block.getGrassType();
    }
}
