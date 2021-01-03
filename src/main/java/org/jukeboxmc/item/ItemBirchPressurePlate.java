package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBirchPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchPressurePlate extends Item {

    public ItemBirchPressurePlate() {
        super( "minecraft:birch_pressure_plate", -151 );
    }

    @Override
    public Block getBlock() {
        return new BlockBirchPressurePlate();
    }
}
