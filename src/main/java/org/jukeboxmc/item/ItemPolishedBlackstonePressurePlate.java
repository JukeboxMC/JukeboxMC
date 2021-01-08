package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBlackstonePressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstonePressurePlate extends Item {

    public ItemPolishedBlackstonePressurePlate() {
        super( "minecraft:polished_blackstone_pressure_plate", -295 );
    }

    @Override
    public BlockPolishedBlackstonePressurePlate getBlock() {
        return new BlockPolishedBlackstonePressurePlate();
    }
}
