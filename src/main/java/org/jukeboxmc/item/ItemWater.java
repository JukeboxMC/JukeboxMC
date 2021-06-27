package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWater;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWater extends Item {

    public ItemWater() {
        super ( "minecraft:water" );
    }

    @Override
    public BlockWater getBlock() {
        return new BlockWater();
    }
}
