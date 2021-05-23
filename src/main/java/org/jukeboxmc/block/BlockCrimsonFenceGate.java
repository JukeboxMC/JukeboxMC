package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCrimsonFenceGate;

public class BlockCrimsonFenceGate extends BlockFenceGate {

    public BlockCrimsonFenceGate() {
        super("minecraft:crimson_fence_gate");
    }

    @Override
    public ItemCrimsonFenceGate toItem() {
        return new ItemCrimsonFenceGate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRIMSON_FENCE_GATE;
    }

}