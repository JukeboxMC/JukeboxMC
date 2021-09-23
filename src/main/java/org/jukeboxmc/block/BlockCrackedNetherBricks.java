package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCrackedNetherBricks;
import org.jukeboxmc.item.type.ItemToolType;

public class BlockCrackedNetherBricks extends Block {

    public BlockCrackedNetherBricks() {
        super("minecraft:cracked_nether_bricks");
    }

    @Override
    public ItemCrackedNetherBricks toItem() {
        return new ItemCrackedNetherBricks();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRACKED_NETHER_BRICKS;
    }


    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}