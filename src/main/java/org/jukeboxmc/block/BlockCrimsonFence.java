package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCrimsonFence;

public class BlockCrimsonFence extends Block {

    public BlockCrimsonFence() {
        super("minecraft:crimson_fence");
    }

    @Override
    public ItemCrimsonFence toItem() {
        return new ItemCrimsonFence();
    }

    @Override
    public BlockType getType() {
        return BlockType.CRIMSON_FENCE;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}