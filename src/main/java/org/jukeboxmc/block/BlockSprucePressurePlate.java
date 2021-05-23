package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSprucePressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSprucePressurePlate extends BlockPressurePlate {

    public BlockSprucePressurePlate() {
        super( "minecraft:spruce_pressure_plate" );
    }

    @Override
    public ItemSprucePressurePlate toItem() {
        return new ItemSprucePressurePlate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SPRUCE_PRESSURE_PLATE;
    }

}
