package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemAcaciaFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAcaciaFenceGate extends BlockFenceGate {

    public BlockAcaciaFenceGate() {
        super( "minecraft:acacia_fence_gate" );
    }

    @Override
    public ItemAcaciaFenceGate toItem() {
        return new ItemAcaciaFenceGate();
    }

    @Override
    public BlockType getType() {
        return BlockType.ACACIA_FENCE_GATE;
    }
}
