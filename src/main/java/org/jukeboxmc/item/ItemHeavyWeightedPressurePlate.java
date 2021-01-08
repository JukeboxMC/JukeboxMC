package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockHeavyWeightedPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHeavyWeightedPressurePlate extends Item {

    public ItemHeavyWeightedPressurePlate() {
        super( "minecraft:heavy_weighted_pressure_plate", 148 );
    }

    @Override
    public BlockHeavyWeightedPressurePlate getBlock() {
        return new BlockHeavyWeightedPressurePlate();
    }
}
