package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStonePressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStonePressurePlate extends Item {

    public ItemStonePressurePlate() {
        super( "minecraft:stone_pressure_plate", 70 );
    }

    @Override
    public BlockStonePressurePlate getBlock() {
        return new BlockStonePressurePlate();
    }
}
