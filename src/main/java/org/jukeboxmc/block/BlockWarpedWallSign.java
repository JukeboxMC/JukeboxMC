package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWarpedWallSign;

public class BlockWarpedWallSign extends BlockWallSign {

    public BlockWarpedWallSign() {
        super("minecraft:warped_wall_sign");
    }

    @Override
    public ItemWarpedWallSign toItem() {
        return new ItemWarpedWallSign();
    }

    @Override
    public BlockType getType() {
        return BlockType.WARPED_WALL_SIGN;
    }

}