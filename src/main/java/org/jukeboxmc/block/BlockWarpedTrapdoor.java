package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWarpedTrapdoor;

public class BlockWarpedTrapdoor extends BlockTrapdoor {

    public BlockWarpedTrapdoor() {
        super("minecraft:warped_trapdoor");
    }

    @Override
    public ItemWarpedTrapdoor toItem() {
        return new ItemWarpedTrapdoor();
    }

    @Override
    public BlockType getType() {
        return BlockType.WARPED_TRAPDOOR;
    }

}