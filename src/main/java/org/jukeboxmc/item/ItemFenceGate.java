package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockOakFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFenceGate extends Item {

    public ItemFenceGate() {
        super ( "minecraft:fence_gate" );
    }

    @Override
    public BlockOakFenceGate getBlock() {
        return new BlockOakFenceGate();
    }
}
