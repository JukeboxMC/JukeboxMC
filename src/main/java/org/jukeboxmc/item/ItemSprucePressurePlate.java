package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSprucePressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSprucePressurePlate extends Item {

    public ItemSprucePressurePlate() {
        super( "minecraft:spruce_pressure_plate", -154 );
    }

    @Override
    public BlockSprucePressurePlate getBlock() {
        return new BlockSprucePressurePlate();
    }
}
