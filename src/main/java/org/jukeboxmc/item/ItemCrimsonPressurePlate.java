package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCrimsonPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonPressurePlate extends Item {

    public ItemCrimsonPressurePlate() {
        super ( "minecraft:crimson_pressure_plate" );
    }

    @Override
    public BlockCrimsonPressurePlate getBlock() {
        return new BlockCrimsonPressurePlate();
    }
}
