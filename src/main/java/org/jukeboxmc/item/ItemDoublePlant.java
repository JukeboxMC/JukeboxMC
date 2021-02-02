package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDoublePlant;
import org.jukeboxmc.block.type.PlantType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDoublePlant extends Item {

    public ItemDoublePlant() {
        super( "minecraft:double_plant", 175 );
    }

    @Override
    public BlockDoublePlant getBlock() {
        return new BlockDoublePlant();
    }

    public void setPlantType( PlantType plantType ) {
        this.setMeta( plantType.ordinal() );
    }

    public PlantType getPlantType() {
        return PlantType.values()[this.getMeta()];
    }

}
