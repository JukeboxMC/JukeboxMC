package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockOakFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFenceGate extends Item {

    public ItemFenceGate() {
        super( 107 );
    }

    @Override
    public BlockOakFenceGate getBlock() {
        return new BlockOakFenceGate();
    }
}
