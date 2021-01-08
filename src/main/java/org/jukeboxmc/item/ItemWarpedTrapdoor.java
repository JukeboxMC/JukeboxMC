package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedTrapdoor extends Item {

    public ItemWarpedTrapdoor() {
        super( "minecraft:warped_trapdoor", -247 );
    }

    @Override
    public BlockWarpedTrapdoor getBlock() {
        return new BlockWarpedTrapdoor();
    }
}
