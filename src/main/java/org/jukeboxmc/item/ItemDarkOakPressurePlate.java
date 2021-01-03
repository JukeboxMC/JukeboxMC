package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDarkOakPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakPressurePlate extends Item {

    public ItemDarkOakPressurePlate() {
        super( "minecraft:dark_oak_pressure_plate", -152 );
    }

    @Override
    public Block getBlock() {
        return new BlockDarkOakPressurePlate();
    }
}
