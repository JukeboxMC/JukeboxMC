package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedFenceGate extends Item {

    public ItemWarpedFenceGate() {
        super( -259 );
    }

    @Override
    public BlockWarpedFenceGate getBlock() {
        return new BlockWarpedFenceGate();
    }
}
