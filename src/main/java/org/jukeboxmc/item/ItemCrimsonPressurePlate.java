package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCrimsonPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonPressurePlate extends Item {

    public ItemCrimsonPressurePlate() {
        super( -262 );
    }

    @Override
    public BlockCrimsonPressurePlate getBlock() {
        return new BlockCrimsonPressurePlate();
    }
}
