package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemHeavyWeightedPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHeavyWeightedPressurePlate extends BlockPressurePlate {

    public BlockHeavyWeightedPressurePlate() {
        super( "minecraft:heavy_weighted_pressure_plate" );
    }

    @Override
    public ItemHeavyWeightedPressurePlate toItem() {
        return new ItemHeavyWeightedPressurePlate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.HEAVY_WEIGHTED_PRESSURE_PLATE;
    }

}
