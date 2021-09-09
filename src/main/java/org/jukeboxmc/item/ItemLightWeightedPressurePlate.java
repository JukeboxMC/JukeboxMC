package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLightWeightedPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLightWeightedPressurePlate extends Item {

    public ItemLightWeightedPressurePlate() {
        super ( "minecraft:light_weighted_pressure_plate" );
    }

    @Override
    public BlockLightWeightedPressurePlate getBlock() {
        return new BlockLightWeightedPressurePlate();
    }
}
