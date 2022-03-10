package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWoodenPressurePlate;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWoodenPressurePlate extends Item implements Burnable {

    public ItemWoodenPressurePlate() {
        super ( "minecraft:wooden_pressure_plate" );
    }

    @Override
    public BlockWoodenPressurePlate getBlock() {
        return new BlockWoodenPressurePlate();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
