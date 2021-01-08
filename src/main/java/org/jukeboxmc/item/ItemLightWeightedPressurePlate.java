package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLightWeightedPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLightWeightedPressurePlate extends Item {

    public ItemLightWeightedPressurePlate() {
        super( "minecraft:light_weighted_pressure_plate", 147 );
    }

    @Override
    public BlockLightWeightedPressurePlate getBlock() {
        return new BlockLightWeightedPressurePlate();
    }
}
