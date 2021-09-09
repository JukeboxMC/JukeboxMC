package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockFrostedIce;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFrostedIce extends Item {

    public ItemFrostedIce() {
        super ( "minecraft:frosted_ice" );
    }

    @Override
    public BlockFrostedIce getBlock() {
        return new BlockFrostedIce();
    }
}
