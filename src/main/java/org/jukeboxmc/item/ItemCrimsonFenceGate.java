package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCrimsonFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonFenceGate extends Item {

    public ItemCrimsonFenceGate() {
        super( -258 );
    }

    @Override
    public BlockCrimsonFenceGate getBlock() {
        return new BlockCrimsonFenceGate();
    }
}
