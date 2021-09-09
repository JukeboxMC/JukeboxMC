package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWoodenPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWoodenPressurePlate extends BlockPressurePlate {

    public BlockWoodenPressurePlate() {
        super( "minecraft:wooden_pressure_plate" );
    }

    @Override
    public ItemWoodenPressurePlate toItem() {
        return new ItemWoodenPressurePlate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WOODEN_PRESSURE_PLATE;
    }

}
