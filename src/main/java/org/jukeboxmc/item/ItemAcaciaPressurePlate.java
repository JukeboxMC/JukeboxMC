package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAcaciaPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaPressurePlate extends Item {

    public ItemAcaciaPressurePlate() {
        super( "minecraft:acacia_pressure_plate", -150 );
    }

    @Override
    public Block getBlock() {
        return new BlockAcaciaPressurePlate();
    }
}
