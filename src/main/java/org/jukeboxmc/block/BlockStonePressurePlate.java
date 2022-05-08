package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemStonePressurePlate;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStonePressurePlate extends BlockPressurePlate {

    public BlockStonePressurePlate() {
        super( "minecraft:stone_pressure_plate" );
    }

    @Override
    public ItemStonePressurePlate toItem() {
        return new ItemStonePressurePlate();
    }

    @Override
    public BlockType getType() {
        return BlockType.STONE_PRESSURE_PLATE;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }
}
