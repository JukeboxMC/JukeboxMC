package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWarpedFence;

public class BlockWarpedFence extends Block {

    public BlockWarpedFence() {
        super("minecraft:warped_fence");
    }

    @Override
    public ItemWarpedFence toItem() {
        return new ItemWarpedFence();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WARPED_FENCE;
    }

}