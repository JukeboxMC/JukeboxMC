package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaFenceGate extends Item {

    public ItemAcaciaFenceGate() {
        super( "minecraft:acacia_fence_gate", 187 );
    }

    @Override
    public BlockAcaciaFenceGate getBlock() {
        return new BlockAcaciaFenceGate();
    }
}
