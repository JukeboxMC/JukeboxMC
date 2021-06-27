package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockExposedCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemExposedCopper extends Item{

    public ItemExposedCopper() {
        super( "minecraft:exposed_copper" );
    }

    @Override
    public BlockExposedCopper getBlock() {
        return new BlockExposedCopper();
    }
}
