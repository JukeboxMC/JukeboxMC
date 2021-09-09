package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBirchPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBirchPressurePlate extends BlockPressurePlate {

    public BlockBirchPressurePlate() {
        super( "minecraft:birch_pressure_plate" );
    }

    @Override
    public ItemBirchPressurePlate toItem() {
        return new ItemBirchPressurePlate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BIRCH_PRESSURE_PLATE;
    }

}
