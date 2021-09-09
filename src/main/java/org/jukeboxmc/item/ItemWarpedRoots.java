package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedRoots;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedRoots extends Item {

    public ItemWarpedRoots() {
        super ( "minecraft:warped_roots" );
    }

    @Override
    public BlockWarpedRoots getBlock() {
        return new BlockWarpedRoots();
    }
}
