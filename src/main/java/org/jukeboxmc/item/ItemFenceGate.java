package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFenceGate extends Item {

    public ItemFenceGate() {
        super( "minecraft:fence_gate", 107 );
    }

    @Override
    public BlockFenceGate getBlock() {
        return new BlockFenceGate();
    }
}
