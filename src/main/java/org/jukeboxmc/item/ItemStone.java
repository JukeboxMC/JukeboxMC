package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStone;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.StoneType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStone extends Item {

    public ItemStone( int blockRuntimeId ) {
        super( 1, blockRuntimeId );
    }

    @Override
    public BlockStone getBlock() {
        return (BlockStone) BlockType.getBlock( this.blockRuntimeId );
    }

    public StoneType getStoneType() {
        return this.getBlock().getStoneType();
    }

}
