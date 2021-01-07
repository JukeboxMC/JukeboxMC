package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDoublePlant;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDoublePlant extends Item {

    public ItemDoublePlant() {
        super( "minecraft:double_plant", 175 );
    }

    @Override
    public Block getBlock() {
        return new BlockDoublePlant();
    }

    public void setPlantType( PlantType plantType ) {
        this.setMeta( plantType.ordinal() );
    }

    public PlantType getPlantType() {
        return PlantType.values()[this.getMeta()];
    }

    public enum PlantType {
        SUNFLOWER,
        LILAC,
        DOUBLE_TALLGRASS,
        LARGE_FERN,
        ROSE_BUSH,
        PEONY
    }
}
