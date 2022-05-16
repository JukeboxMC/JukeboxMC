package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMangrovePressurePlate;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangrovePressurePlate extends Block {

    public BlockMangrovePressurePlate() {
        super( "minecraft:mangrove_pressure_plate" );
    }

    @Override
    public Item toItem() {
        return new ItemMangrovePressurePlate();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_PRESSURE_PLATE;
    }
}