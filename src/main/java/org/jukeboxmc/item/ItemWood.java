package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.BlockWood;
import org.jukeboxmc.block.type.WoodType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWood extends Item {

    public ItemWood( int blockRuntimeId ) {
        super( -212, blockRuntimeId );
    }

    @Override
    public BlockWood getBlock() {
        return (BlockWood) BlockType.getBlock( this.blockRuntimeId );
    }

    public WoodType getWoodType() {
        return this.getBlock().getWoodType();
    }

}
