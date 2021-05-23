package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBlackstonePressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstonePressurePlate extends Item {

    public ItemPolishedBlackstonePressurePlate() {
        super( -295 );
    }

    @Override
    public BlockPolishedBlackstonePressurePlate getBlock() {
        return new BlockPolishedBlackstonePressurePlate();
    }
}
