package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedNylium;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedNylium extends Item {

    public ItemWarpedNylium() {
        super( "minecraft:warped_nylium", -233 );
    }

    @Override
    public BlockWarpedNylium getBlock() {
        return new BlockWarpedNylium();
    }
}
