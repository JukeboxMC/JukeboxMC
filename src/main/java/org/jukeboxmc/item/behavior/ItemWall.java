package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockCobblestoneWall;
import org.jukeboxmc.block.data.WallType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWall extends Item {

    private final BlockCobblestoneWall block;

    public ItemWall( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.COBBLESTONE_WALL);
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemWall( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.COBBLESTONE_WALL);
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemWall setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemWall setWallType( WallType wallType ) {
        this.blockRuntimeId = this.block.setWallBlockType( wallType ).getRuntimeId();
        return this;
    }

    public WallType getWallType() {
        return this.block.getWallBlockType();
    }
}
