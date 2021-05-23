package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDarkOakFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakFenceGate extends Item {

    public ItemDarkOakFenceGate() {
        super( 186 );
    }

    @Override
    public BlockDarkOakFenceGate getBlock() {
        return new BlockDarkOakFenceGate();
    }
}
