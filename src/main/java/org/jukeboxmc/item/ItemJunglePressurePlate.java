package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJunglePressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJunglePressurePlate extends Item {

    public ItemJunglePressurePlate() {
        super( -153 );
    }

    @Override
    public BlockJunglePressurePlate getBlock() {
        return new BlockJunglePressurePlate();
    }
}
