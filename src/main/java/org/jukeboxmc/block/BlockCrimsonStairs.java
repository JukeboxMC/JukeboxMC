package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCrimsonStairs;

public class BlockCrimsonStairs extends BlockStairs {

    public BlockCrimsonStairs() {
        super("minecraft:crimson_stairs");
    }

    @Override
    public ItemCrimsonStairs toItem() {
        return new ItemCrimsonStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.CRIMSON_STAIRS;
    }

}