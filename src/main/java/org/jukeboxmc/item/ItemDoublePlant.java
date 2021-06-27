package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDoublePlant;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.PlantType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDoublePlant extends Item {

    public ItemDoublePlant( int blockRuntimeId ) {
        super( "double_plant", blockRuntimeId );
    }

    @Override
    public BlockDoublePlant getBlock() {
        return (BlockDoublePlant) BlockType.getBlock( this.blockRuntimeId );
    }

    public PlantType getPlantType() {
        return this.getBlock().getPlantType();
    }

}
