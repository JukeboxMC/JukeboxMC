package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedFungus;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedFungus extends Item {

    public ItemWarpedFungus() {
        super( -229 );
    }

    @Override
    public BlockWarpedFungus getBlock() {
        return new BlockWarpedFungus();
    }
}
