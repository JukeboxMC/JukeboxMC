package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDarkOakPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDarkOakPressurePlate extends BlockPressurePlate {

    public BlockDarkOakPressurePlate() {
        super( "minecraft:dark_oak_pressure_plate" );
    }

    @Override
    public ItemDarkOakPressurePlate toItem() {
        return new ItemDarkOakPressurePlate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DARK_OAK_PRESSURE_PLATE;
    }

}
